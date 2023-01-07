package com.bitstudy.app.service;


import com.bitstudy.app.domain.type.SearchType;
import com.bitstudy.app.dto.ArticleDto;
import com.bitstudy.app.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/** 기능 관련된 로직을 다 여기다가 짤거임.  */

@Service
@RequiredArgsConstructor // 필수 필드에 대한 생성자를 자동으로 만들어주는 롬복 애너테이션
@Transactional // 이 클래스 동작하다가 하나라도 잘못되면 다시 롤백 시켜라 라는 뜻
public class ArticleService {

    private final ArticleRepository articleRepository; // 아티클 관련 서비스 이기 때문에 ArticleRepository 필요

    // 검색용
    @Transactional(readOnly = true) // 트랜잭션을 읽기 전용 모드로 설정. 실수로 커밋해도 flush 가 되지 않아서 엔티티가 등록, 수정, 삭제 가 되지 않는다.
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        return Page.empty();

        /* page: 전체 데이터 건수 조회 (데이터를 다 가지고 있다)
                getTotalElements(): 개수 뽑기
                getTotalPages(): 별도의 size 를 줘서 총 페이지 개수
                getNumber(): 가져온 페이지의 번호를 뽑아준다.

        * pageable: 페이징 기능
               JPA에서 DB 쿼리 날릴떄 limit 를 날려서 데이터 가져옴
        */

    }



}
