package com.bitstudy.app.repository;//package com.bitstudy.app.repository;


import com.bitstudy.app.config.JpaConfig;
import com.bitstudy.app.domain.Article;
import com.bitstudy.app.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

//import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 슬라이드 테스트
/** 슬라이드 테스트란 지난번 TDD 때 각 메서드들 다 남남으로 서로를 알아보지 못하게 만들었었다. 이것처럼 메서드들 각각 테스트한 결과를 서로 못보게 잘라서 만드는것 */

@Import(JpaConfig.class)
/** 원래대로라면 JPA 에서 모든 정보를 컨트롤 해야되는데 JpaConfig 의 경우는 읽어오지 못한다. 이유는 이건 시스템에서 만든게 아니라 우리가 별도로 만든 파일이기 때문. 그래서 따로 import를 해줘야 한다.
 안하면 config 안에 명시해놨던 JpaAuditing 기능이 동작하지 않는다.
 * */
@DisplayName("JPA 테스트")
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    /* 원래는 둘 다 @Autowired가 붙어야 하는데, JUnit5 버전과 최신 버전의 스프링 부트를 이용하면 Test에서 생성자 주입패턴을 사용할 수 있다.  */

    private final UserAccountRepository userAccountRepository;


    /* 생성자 만들기 - 여기서는 다른 파일에서 매개변수로 보내주는걸 받는거라서 위에랑 상관 없이 @Autowired 를 붙여야 함 */
    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }


    /* select 테스트 */
    @DisplayName("select 테스트")
    @Test
    void selectTest() {
        /** 셀렉팅을 할거니까 articleRepository 를 기준으로 테스트 할거임.
            maven방식: dao -> mapper 로 정보 보내고 DB 갔다 와서 C 까지 돌려보낼건데 dao에서 DTO를 list에 담아서 return
         * */

        List<Article> articles  =  articleRepository.findAll();

        /** assertJ 를 이용해서 테스트 할거임
         * articles 가 NotNull 이고 사이즈가 ?? 개면 통과
         *
         * * */
        assertThat(articles).isNotNull().hasSize(100);

    }

    /* insert 테스트 */
    @DisplayName("insert 테스트")
    @Test
    void insertTest() {
        /** 기존의 article 개수를 센 다음에, insert 하고, 기존거보다 현재꺼가 1 차이가 나면 insert 제대로 됐다는 뜻. */

        // 기존 카운트 구하기
        long prevCount = articleRepository.count();

        // insert 하기
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("bitstudy","asdf",null,null,null));
        Article article =  Article.of(userAccount,"제목","내용","#해시태그"); // new Article
        articleRepository.save(article);

        // 기존꺼랑 현재꺼랑 개수 차이 구하기
        assertThat(articleRepository.count()).isEqualTo(prevCount + 1);

    }


    /* update 테스트 */
    @DisplayName("update 테스트")
    @Test
    void updateTest() {
        Article article = articleRepository.findById(1L).orElseThrow();

        String updateHashtag = "#abcd";
        article.setHashtag(updateHashtag);
        //articleRepository.save(article);
        Article savedArticle = articleRepository.saveAndFlush(article);

        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updateHashtag);
//        assertThat(savedArticle.getHashtag()).isEqualTo(updateHashtag);
    }


    /* delete 테스트 */
    @DisplayName("delete 테스트")
    @Test
    void deleteTest() {

        Article article = articleRepository.findById(1L).orElseThrow();

        long prevArticleCount = articleRepository.count();
        long prevArticleCommentCount = articleCommentRepository.count(); // 데이터베이스에 있는 모든 댓글의 수
        int deletedCommentSize = article.getArticleComments().size(); // 해당 게시글에 딸려있는 댓글의 수

        articleRepository.delete(article);

        /* 테스트 통과 조건 - 2번에서 구한거랑 여기서 구하는 개수가 1 차이나는 경우 */
        assertThat(articleRepository.count()).isEqualTo(prevArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(prevArticleCommentCount - deletedCommentSize);



    }

}



























