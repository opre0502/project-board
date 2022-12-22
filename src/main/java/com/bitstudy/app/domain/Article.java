package com.bitstudy.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@EntityListeners(AuditingEntityListener.class) /* 이거 없으면 테스트 할때 createdAt 때문에 에러남(Ex04 관련) */
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity /* 1) Lombok을 이용해서 클래스를 엔티티로 변경. @Entitiy가 붙은 클래스는 JPA가 관리하게 된다.
                그래서 기본키(PK)가 뭔지 알려줘야 한다. 그게 @Id 에너테이션 이다. */
@Getter /* 2) getter/setter/toString 등의 롬복 어노테이션 사용 */ // 롬복의 @Getter를 쓰면 알아서 모든 필드이 getter 들이 생성된다.
@ToString
public class Article {

    @Id // '전체 필드 중에서 이게 PK다' 라고 말해주는것. @Id 가 없으면 @Entity 에러남.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 필드가 auto_increment인 경우 @GeneratedValue을 써서 자동으로 값이 생성되게 해줘야 한다. 기본키 전략
    private Long id;

    @Setter @Column(nullable = false) private String title;   // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문
    @Setter private String hashtag; // 해시태그

    /* 양방향 */
    @OrderBy("id")  // 양방향 바인딩을 할건데 정렬 기준을 id로 하겠다는 뜻
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude /** 이거 중요. 맨 위에 @ToString 이 있는데 마우스 올려보면 '@ToString includes~ lazy load 어쩌고' 나온다.
     이건 퍼모먼스, 메모리 저하를 일으킬수 있어서 성능적으로 않좋은 영향을 줄 수 있다. 그래서 해당 필드를 가려주세요 하는 거 */
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    // 메타데이터
    @CreatedDate
    @Setter @Column(nullable = false)
    private LocalDateTime createdAt;    // 생성일시

    @CreatedBy
    @Setter @Column(nullable = false, length = 100)
    private String createdBy;    // 생성자

    @LastModifiedDate
    @Setter @Column(nullable = false)
    private LocalDateTime modifiedAt;   // 생성일시

    @LastModifiedBy
    @Setter @Column(nullable = false, length = 100)
    private String modifiedBy;  // 수정자

    protected Article() {}

    /** 사용자가 입력하는 값만 받기. 나머지는 시스템이 알아서 하게 해주면 됨. */
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title,content,hashtag);
    }


    @Override
    public boolean equals(Object o){
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         Article article = (Article) o;
         return id.equals(article.id);
//         return id != null && id.equals(article.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
