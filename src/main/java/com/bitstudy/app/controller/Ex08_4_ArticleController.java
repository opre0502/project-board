package com.bitstudy.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/** 뷰 엔드포인트 관련 컨트롤러
 *
 * 엑셀 api 에 보면 정의해놓은 view 부분에 url 들이 있다. 그거 보면서 하면됨
    /articles	                    GET	게시판 페이지
    /articles/{article-id}	        GET	게시글 페이지
    /articles/serach	            GET	게시판 검색 전용 페이지
    /articles/serach-hashtag	    GET	게시판 해시태그 검색 전용 페이지
 *
 * 
 *
 * - Thymeleaf - 
 *  뷰 파일은 HTML 로 작업할건데, 타임리프를 설치 함으로서 스프링은 이제 html 파일을 마크업으로 보지 않고, 타입리프 템플릿 파일로 인식한다.
 *  그래서 이 HTML 파일들을 아무데서나 작성할 수 없고, resources > templates 폴더 안에만 작성 가능하다.
 *  그 외의 css, img, 그리고 js 들은 resources > static 폴더 안에 작성 가능
 * */

@Controller
@RequestMapping("/articles08") // 모든 경로들은 /articles 로 시작하니까 클래스 레벨에 1차로 @RequestMapping("/articles") 걸어놨음
public class Ex08_4_ArticleController {

    @GetMapping
    public String articles(ModelMap map) {
        /* ModelMap: 테스트파일에서 .andExpect(model().attributeExists("articles")); 를 이용해서 articles 라는 키값으로 데이터를 넣어주기로 했으니까 필요함.

         * Model 과 ModelMap는 같은거임. 차이점은 Model 은 인터페이스, ModelMap 은 클래스(구현체)
         */
        map.addAttribute("articles", List.of()); // 키: articles, 값: 그냥 list
        return "articles/index";
    }
    
}
