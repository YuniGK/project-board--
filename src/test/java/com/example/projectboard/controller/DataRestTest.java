package com.example.projectboard.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Data REST 테스트")
@SpringBootTest
@Transactional//테스트에서 작동되는 트랜젝션은 RollBack된다.
@AutoConfigureMockMvc
public class DataRestTest {

    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    /*
    1) perform()
        요청을 전송하는 역할을 합니다.
        결과로 ResultActions 객체를 받으며, ResultActions 객체는 리턴 값을 검증하고 확인할 수 있는 andExcpect() 메소드를 제공해줍니다.

    2) get("/mock/blog")
        HTTP 메소드를 결정할 수 있습니다. ( get(), post(), put(), delete() )
        인자로는 경로를 보내줍니다.

    3) params(info)
        키=값의 파라미터를 전달할 수 있습니다.
        여러 개일 때는 params()를, 하나일 때에는 param()을 사용합니다.

    4) andExpect()
        응답을 검증하는 역할을 합니다.
        - 상태 코드 ( status() )
            메소드 이름 : 상태코드
            isOk() : 200
            isNotFound() : 404
            isMethodNotAllowed() : 405
            isInternalServerError() : 500
            is(int status) : status 상태 코드
        - 뷰 ( view() )
            리턴하는 뷰 이름을 검증합니다.
            ex. view().name("blog") : 리턴하는 뷰 이름이 blog인가?
        - 리다이렉트 ( redirect() )
            리다이렉트 응답을 검증합니다.
            ex. redirectUrl("/blog") : '/blog'로 리다이렉트 되었는가?
        - 모델 정보 ( model() )
            컨트롤러에서 저장한 모델들의 정보 검증
        - 응답 정보 검증 ( content() )
            응답에 대한 정보를 검증해줍니다.

    5) andDo(print())
    요청/응답 전체 메세지를 확인할 수 있습니다.
    */
    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception {
        //Given

        //When & Then --존재하는지 확인
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print());
    }

    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticle_thenReturnsArticleJsonResponse() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 게시글 -> 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleCommentsFromArticle_thenReturnsArticleCommentsJsonResponse() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/api/articles/1/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleComments_thenReturnsArticleCommentsJsonResponse() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 댓글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticleComment_thenReturnsArticleCommentJsonResponse() throws Exception {
        //Given

        //When & Then
        mvc.perform(get("/api/articleComments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }
}
