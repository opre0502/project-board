package com.bitstudy.app.repository;

import com.bitstudy.app.domain.ArticleComment;
import com.bitstudy.app.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** */

@RepositoryRestResource
public interface Ex07_5_ArticleCommentRepository extends JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>
        , QuerydslBinderCustomizer<QArticleComment> {

    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {

        bindings.excludeUnlistedProperties(true);

        bindings.including( root.content, root.createAt, root.createBy);

        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createAt).first(DateTimeExpression::eq); // 이건 날짜니까 DateTimeExpression 으로 하고,eq는 equals의 의미. 날짜 필드는 정확한 검색만 되도록 설정. 근데 이렇게 하면 시분초가 다 0 으로 인식됨. 이부분은 별도로 시간 처리할떄 건드릴 것.
        bindings.bind(root.createBy).first(StringExpression::containsIgnoreCase);
    }

    /*  1) 빌드 (ctrl + F9)
    *   2) Hal 가서 체크하기
    *       ex) http://localhost:8080/api/article?createBy=Klaus
    *           http://localhost:8080/api/articleComments?createBy=Klaus 해보기
    *
    * */
}