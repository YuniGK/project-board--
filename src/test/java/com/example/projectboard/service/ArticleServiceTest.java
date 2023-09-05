package com.example.projectboard.service;

import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.type.SearchType;
import com.example.projectboard.dto.ArticleDto;
import com.example.projectboard.dto.ArticleUpdateDto;
import com.example.projectboard.repository.ArticleCommentRepository;
import com.example.projectboard.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks// Mock 객체를 주입시켜준다. - @Mock이나 @Spy로 생성된 mock 객체를 자동으로 주입
    private ArticleService sut;//테스트 대상임을 알려준다.
    @Mock//Mock 객체를 생성(주입 시키는 것 외) - 가짜 객체
    private ArticleRepository articleRepository;
    @Mock
    private ArticleCommentRepository articleCommentRepository;

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList(){
        //Given

        //When
        Page<ArticleDto> articles =
                sut.searchArticles(SearchType.TITLE, "search keyword");
        //제목, 본문, ID, 닉네임, 해시태그 - 검색이 되는 항목

        //Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenArticledId_whenSearchingArticle_thenReturnsArticle(){
        //Given

        //When
        ArticleDto articles = sut.searchArticle(1L);

        //Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle(){
        //Given
        ArticleDto dto = ArticleDto.of(
                LocalDateTime.now(), "yni", "new title", "content", "java"
        );

        /* mock - doNothing()과 동일하다. ---> void 메서드이다.
        * save는 void메서드가 아니여서 오류가 발생한다. */
        //willDoNothing().given(articleRepository).save(any(Article.class));//any 해당 클래스에 맞는 것을 저장한다.
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //When
        sut.saveArticle(dto);

        //Then
        then(articleRepository).should().save(any(Article.class));//save를 호출했는지 확인한다.
    }

    @DisplayName("게시글 ID와 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    void givenArticleIdAndModifiendInfo_whenUpdatingArticle_thenUpdatesArticle(){
        //Given
        given(articleRepository.save(any(Article.class))).willReturn(null);

        //When
        sut.updatearticle(1L, ArticleUpdateDto.of("title", "content", "#java"));

        //Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 ID를 입력하면 게시글을 삭제한다.")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
        //Given
        willDoNothing().given(articleRepository).delete(any(Article.class));

        //When
        sut.deleteArticle(1L);

        //Then
        then(articleRepository).should().delete(any(Article.class));//delete를 호출했는지 확인한다.
    }
}