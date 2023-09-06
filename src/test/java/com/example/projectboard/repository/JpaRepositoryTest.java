package com.example.projectboard.repository;

import com.example.projectboard.config.JpaConfig;
import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//테스트 db가 아닌 설정한 db를 불러와서 테스트한다.
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository
            , @Autowired ArticleCommentRepository articleCommentRepository
            , @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.userAccountRepository = userAccountRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select test")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        //Given
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("yuni", "pw", null, null, null));

        Article article = Article.of(userAccount, "new article", "new count", "spring");

        //When
        articleRepository.save(article);

        //Then
        assertThat(articleRepository.count()).isEqualTo(previousCount+1);
    }

    @DisplayName("insert test")
    @Test
    void givenTestData_whenInserting_thenWorksFine(){
        //Given
        long previousCount = articleRepository.count();//기존 리스트의 갯수를 확인
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("yuni", "pw", null, null, null));

        //When
        Article savedArticle = articleRepository.save(
                Article.of(userAccount, "new article", "new content", "#spring")
        );

        //Then
        assertThat(articleRepository.count()).isEqualTo(previousCount+1);
    }

    @DisplayName("update test")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){
        //Given
        Article article = articleRepository.findById(1l).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        //When
        //테스트의 경우 @DataJpaTest에 의해 트랜젝션이 되어 저장 시, flush를 해줘야 반영됐는지 확인이 가능하다.
        Article savedArticle = articleRepository.saveAndFlush(article);

        //Then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag);//업데이트 되었는지 확인한다.
    }

    @DisplayName("delete test")
    @Test
    void givenTestData_whenDeleting_thenWorksFine(){
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long precviousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        //When
        articleRepository.delete(article);

        //Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount-1);
        assertThat(articleCommentRepository.count()).isEqualTo(precviousArticleCommentCount-deletedCommentsSize);

    }

}