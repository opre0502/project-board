package com.bitstudy.app.repository;

import com.bitstudy.app.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

/** TDD를 위해서 임시로 만들어놓은 저장소 (이거로 DB에 접근할거다)
 *
 * - TDD 만드는 방법
 * 1) 우클릭  > Go to > Test (ctrl + shift + T)
 * 2) JUint5 버전인지 확인
 * 3) 이름 ArticleRepositoryTest 를 JpaRepositoryTest 로 변경
 * */
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
