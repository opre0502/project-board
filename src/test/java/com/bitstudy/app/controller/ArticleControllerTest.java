package com.bitstudy.app.controller;

import com.bitstudy.app.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/* 지금 상태로 테스트 돌리면 콘솔창에 401이 뜬다. (401: 파일을 찾긴 찾았는데 인증을 못받아서 못들어간다 라는 뜻.)
*   이유는 기본 웹 시큐리티를 불러와서 그런것.
*   config > SecurityConfig 를 읽어오게 하면 된다.
*   방법: @Import(SecurityConfig.class) 를 클래스 레벨에 넣어서 현재 이 test 코드에서 아아볼수 있게
* */

@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
@DisplayName("view 컨트롤러 - 게시글")
class ArticleControllerTest {

    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mvc){
        this.mvc = mvc;
    }

    /* 1) 게시판 (리스트) 페이지 */
    @Test
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상호출")
    public void articlesAll() throws Exception{
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));
    }

    /* 2) 게사글 (상세) 페이지 */
    @Test
    @DisplayName("[view][GET] 게사글 (상세) 페이지 - 정상호출")
    public void articlesOne() throws Exception{
        mvc.perform(get("/articles/1")) /* 테스트니까 그냥 1번 글 가져와라 할거임. */
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("article/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));
                // 상세페이지에는 댓글들도 같이 오니까 모델 어트리뷰트에 articleComments 키가 있는지 확인
    }
    /* 3) 게사글 검색 전용 */
    @Test
    @DisplayName("[view][GET] 게사글 검색 전용 페이지 - 정상호출")
    public void articlesSearch() throws Exception{
        mvc.perform(get("/articles/Search")) /* 테스트니까 그냥 1번 글 가져와라 할거임. */
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/Search"));
        // 상세페이지에는 댓글들도 같이 오니까 모델 어트리뷰트에 articleComments 키가 있는지 확인
    }

    /* 4) 해시태그 검색 전용 페이지 */
    @Test
    @DisplayName("[view][GET] 게시글 해시태그 전용 페이지 - 정상호출")
    public void articlesSearchHashtag() throws Exception{
        mvc.perform(get("/articles/Search-Hashtag")) /* 테스트니까 그냥 1번 글 가져와라 할거임. */
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/Search-Hashtag"));
    }
}