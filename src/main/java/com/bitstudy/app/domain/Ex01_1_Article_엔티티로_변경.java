package com.bitstudy.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/** 할일: Lombok 사용하기
 * 주의: maven 떄랑 같은 방식인 것들도 이름이 다르게 되어 있으니 헷갈리지 않게 주의!
 *
 * 순서
 * 1) Lombok 을 이용해서 클래스를 엔티티로 변경
 * 2) getter/setter, toString 등의 롬복 어노테이션 사용
 * 3) 동등성, 동일성 비교할 수 있는 코드 넣어볼거임
 *
 * */

/* @Table - 엔티티와 매핑할 정보를 지정하고
*           사용법) @Index(name = "원하는명칭", columnList = "사용할 테이블명")
*                         name 부분을 생략하면 원래 이름 사용.
* @Index - 데이터베이스 인덱스는 추가, 쓰기, 및 저장공간을 희생해서 테이블에 대한 데이터 검색 속도를 향상시키는 데이터 구조
*          @Entity와 세트로 사용
*  */
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
public class Ex01_1_Article_엔티티로_변경 {

    @Id // '전체 필드 중에서 이게 PK다' 라고 말해주는것. @Id 가 없으면 @Entity 에러남.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 필드가 auto_increment인 경우 @GeneratedValue을 써서 자동으로 값이 생성되게 해줘야 한다. 기본키 전략
    private Long id;

    /* @Setter  도 @Getter 처럼 클래스 단위로 걸 수 있는데, 그렇게 하면 모든 필드에 접근이 가능해진다.
    *   그런데 id는 내가 부여하는게 아니라 JPA 에서 자동으로 부여해주는 번호이다. 메타데이터들도 자동으로 JPA가 셋팅 하게 만들어야 한다. 그래서 id와 메타데이터는 @Setter 가 필요없다.
    *   @Setter 의 경우는 지금처럼 필요한 필드에만 주는걸 연습하자. (회사마다 다름)*/
    /** @Column - 해당 컬럼이 not null 인경우 -> @Column(nullable = false) 써줌.
     * 기본값은 true 라서 @Column을 아예 안쓰면 null 가능.
     * @Column(nullable=false, length="숫자") -> 숫자 안쓰면 기본값 255 적용
     * */
    @Setter @Column(nullable = false) private String title;   // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문
    @Setter private String hashtag; // 해시태그

    /* 양방향
    *
    * */


    /*  jpa auditing: jpa 에서 자동으로 세팅하게 해줄떄 사용가능 기능
        이거 하려면 config 파일이 별도로 있어야 한다.
        config 패키지 만들어서 JpaConfig 클래스 만들자
     * */
    // 메타데이터
    @CreatedDate
    @Setter @Column(nullable = false)
    private LocalDateTime createdAt;    // 생성일시

    @CreatedBy
    @Setter @Column(nullable = false, length = 100)
    private String createdBy;    // 생성자
    /** 다른 생성일시 같은것들은 알아낼 수 있는데, 최초 생성자는 (현재 코드 상태)인증받고 오지 않았기 떄문에 따로 알아낼 수가 없다.
     *  이때 아까 만든 jpaConfig 파일을 사용한다.     */

    @LastModifiedDate
    @Setter @Column(nullable = false)
    private LocalDateTime modifiedAt;   // 생성일시

    @LastModifiedBy
    @Setter @Column(nullable = false, length = 100)
    private String modifiedBy;  // 수정자

    /* 위에 처럼 어노테이션을 붙여주기만하면 auditing이 작동한다.
    * @CreatedDate  : 최초에 insert 할떄 자동으로 한번 넣어준다.
    * @CreatedBy    : 최초에 insert 할때 자동으로 한번 넣어준다.
    * @LastModifiedDate : 작성 당시의 시간을 실시간으로 넣어준다
    * @LastModifiedBy   : 작성 당시의 작성자의 이름을 실시간으로 넣어준다.
    * */

    /** Entity 를 만들때는 무조건 기본 생성자가 필요하다.
     *  public 또는 protected만 가능한데, 평생 아무데서도 기본생성자를 안쓰이게 하고싶어서 protected로 변경함
     *  */
    protected Ex01_1_Article_엔티티로_변경() {}

    /** 사용자가 입력하는 값만 받기. 나머지는 시스템이 알아서 하게 해주면 됨. */
    private Ex01_1_Article_엔티티로_변경(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Ex01_1_Article_엔티티로_변경 of(String title, String content, String hashtag) {
        return new Ex01_1_Article_엔티티로_변경(title,content,hashtag);
    }
    /* 정적 팩토리 메서드 (factory method pattern 중에 하나)
    *  정적 팩토리 메서드란 객체 생성 역할을 하는 클래스 메서드 라는 뜻
    *  of 메시드를 이용해서 위에 있는 private 생성자를 직접적으로 사용해서 객체를 생성하게 하는 방법
    *  !!!중요: 무조건 static 으로 놔야한다.
    *
    *  장점
    *   1) static 이기 떄문에 new 를 이용해서 생성자를 만들지 않아도 된다.
    *   2) return 을 가지고 있기 때문에 상속시 값을 확인할 수 있다.
    *   3) (중요) 객체 생성을 캡슐화 할 수 있다.
    *
    * */

    /**
     * public: 제한 없음.
     * protected : 동일한 패키지, 파생클래스 에서만 접근 가능
     * default : 동일한 패키지 내에서만 접근 가능
     * private : 자기 자신의 클래스 내에서만 접근 가능
     * */

    /** public: 제한없음. ...*/

    /* 엄청 어려운 개념!!
    * 만약에 Article 클래스를 이용해서 게시글들을 list 에 담아서 화면을 구성할건데, 그걸 하려면 Collection을 이용해야 한다.
        Collection: 객체의 모음(그룹)
                    자바가 제공하는 최상위 컬렉션(인터페이스)
                    하이버네이트는 중복을 허용하고 순서를 보장하지 않는다고 가정.
        Set: 중복 허용 안함, 순서도 보장하지 않음
        List: 중복 허용, 순서 있음
        Map: key, value 구조로 되어 있는 특수 컬렉션

        list에 넣거나 또는 list 에 있는 중복요소를 제거하거나 정렬할때 비교를 할 수 있어야 하기때문에
        동일성, 동등성 비교를 할 수 있는 equals 랑 hashcode 를 구현해야 한다.

        모든데이터들을 비교해도 되지만, 다 불러와서 비교하면 느려질수 있다,=.
        사일 id 만 같으면 두 엔티티가 같다는 뜻이니까 id만 가지고 비교하는걸 구현하자

        체크 박스 여러번 나올건데 id 만 체크해서 만들면됨
     */


    @Override
    public boolean equals(Object o){
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         Ex01_1_Article_엔티티로_변경 article = (Ex01_1_Article_엔티티로_변경) o;
         return id.equals(article.id);
//         return id != null && id.equals(article.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /** ==와 equals 차이
     *
     * == : 동일성 비교, 값이랑 주소값까지 비교.
     * equals : 동등성 비교, 값 만 본다.
     * hashCode : 객체를 실벽하는 Integer 값.
     *            객체가 가지고 있는 데이터를 특정 알고리즘을 적용해서 계산된 정수값을 hashcode 라고 함.
     *            사용하는 이유: 객체를 비교할 때 드는 비용이 낮다.
     *
     *      자바에서 2개의 객체를 비교할때는 equals()를 사용하는데,
     *      여러 객체를 비교할때는 equals()를 사용하면 Integer를 비교하는데 많은 시간이 소요된다.
     *
     * */
<<<<<<< HEAD

=======
>>>>>>> #13-DB
}
