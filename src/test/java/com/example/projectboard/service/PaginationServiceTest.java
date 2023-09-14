package com.example.projectboard.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("비즈니스 로직 - 페이지네이션")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)
class PaginationServiceTest {

    private final PaginationService sut;

    public PaginationServiceTest(@Autowired PaginationService paginationService) {
        this.sut = paginationService;
    }

    @DisplayName("현재 페이지 번호와 총 페이지 수를 주면, 페이징 바 리스트를 만들어 준다.")
    @MethodSource
    /* @MethodSouce안에 static으로 선언한 메서드명을 작성한다.
    * 보다 복잡한 인수를 제공하는 방법 중 한 가지는 method를 argument source로 사용한다.
    * 다른 테스트 클래스 간에 인수를 공유하는 것이 유용하다. */
    @ParameterizedTest(name = "[{index}] 현재 페이지: {0}, 총 페이지: {1} => {2}")/* 다양한 인수로 테스트를 여러번 실행한다. */
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers
    (int currentPageNumber, int totalPages, List<Integer> expected){//마지막에는 검증을 원하는 값을 넣어준다.
        //Given

        //When
        List<Integer> actual = sut.getPaginationBarNumbers(currentPageNumber, totalPages);

        //Then
        /* assertThat(T actual, Matcher<? super T> matcher)의 형태로 메서드를 사용하여 두 값을 비교할 수 있다.
        첫번째 파라미터에는 비교대상 값을, 두번째 파라미터로는 비교로직이 담긴 Matcher가 사용된다. */
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers() {
        return Stream.of(
                arguments(0, 13, List.of(0, 1, 2, 3, 4)),
                arguments(1, 13, List.of(0, 1, 2, 3, 4)),
                arguments(2, 13, List.of(0, 1, 2, 3, 4)),
                arguments(3, 13, List.of(1, 2, 3, 4, 5)),
                arguments(4, 13, List.of(2, 3, 4, 5, 6)),
                arguments(5, 13, List.of(3, 4, 5, 6, 7)),
                arguments(6, 13, List.of(4, 5, 6, 7, 8)),
                arguments(10, 13, List.of(8, 9, 10, 11, 12)),
                arguments(11, 13, List.of(9, 10, 11, 12)),
                arguments(12, 13, List.of(10, 11, 12))
        );
    }

    @DisplayName("현재 설정되어 있는 페이지네이션 바의 길이를 알려준다.")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength() {
        // Given

        // When
        int barLength = sut.currentBarLength();

        // Then
        assertThat(barLength).isEqualTo(5);
    }

}