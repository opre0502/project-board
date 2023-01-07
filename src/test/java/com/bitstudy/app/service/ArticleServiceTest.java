package com.bitstudy.app.service;

import com.bitstudy.app.domain.type.SearchType;
import com.bitstudy.app.dto.ArticleDto;
import com.bitstudy.app.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import static org.junit.jupiter.api.Assertions.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


/** 서비스 비지니스 로직은 슬라이스 테스트 기능 사용 안하고 만들어볼거임
    스프링부트 어플리케이션 컨셑그스가 뜨는데 걸리는 시간을 없애려고 한다.
    디펜던시가 추가되야 하는 부분에는 Mocking 을 하는 방식으로 한다.
    그래서 많이 사용하는 라이브러리가 mokito 라는게 있다. (스프링 테스트 패키지에 내장되어 있음.)

 @ExtendWith(MockitoExtension.class) 쓰면 된다.
 */

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    /* Mock을 주입하는 거에다가 @InjectMocks 을 달아줘야 한다. 그 외의 것들 한테는 @Mock 달아준다. */
    @InjectMocks private ArticleService sut; // sut - system under test. 테스트 짤때 사용하는 이름중 하나. 이건 테스트 대상이다 라는 뜻

    @Mock
    private ArticleRepository articleRepository; // 의존하는걸 가져와야 함. (테스트 중간에 mocking 할때 필요)

    /** 테스트 할 기능들 정리
     *  검색
     *  각 게시글 선택하면 해당 상세 페이지로 이동
     *  페이지네이션                              */

    /* 1. 검색 */
    @DisplayName("검색어 없이 게시글 검색, 게시글 리스트를 반환 한다.")
    @Test
    void withoutKeywordreturnArticlesAll () {
        // Given - 페이지 기능을 넣기
        Pageable pageable = Pageable.ofSize(20);    // 한 페이지에 몇개 가져올건지 결정
        given(articleRepository.findAll(pageable)).willReturn(Page.empty());
        /** Pageable - org.springframework.data.domain
         *  given - org.mockito.BDDMockito          */

        // When - 입력 없는지(null) 실제 테스트 돌리는 부분
        Page<ArticleDto> articles = sut.searchArticles(null,null,pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @DisplayName("검색어 이용해서 게시글 검색, 게시글 리스트를 반환 한다.")
    @Test
    void withKeywordreturnArticlesAll () {
        // Given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(searchKeyword,pageable)).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticles(searchType,searchKeyword,pageable);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(searchKeyword,pageable);

    }
}
















