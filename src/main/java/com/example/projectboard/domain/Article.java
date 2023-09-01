package com.example.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})/* 테이블 지정 - index를 여러개 추가하는 방법
    / 테이블에 대한 데이터 검색 작업의 속도를 향상 시키는 데이터 구조 */
//@EntityListeners(AuditingEntityListener.class)//JPA Auditing라는 것을 알려준다.
@Entity
public class Article extends AuditingFields{
    @Id//primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동으로 AUTO_INCREMENT를 하여 기본키를 생성
    /* 엔티티 기본 생성 전략을 정의(기본키 생성을 데이터베이스에게 위임하는 방식)
        - @GeneratedValue(strategy = GenerationType.IDENTITY)
        자동으로 AUTO_INCREMENT를 하여 기본키를 생성
        - @GeneratedValue(strategy = GenerationType.SEQUNCE)
        데이터베이스가 자동으로 기본키를 생성해준다.
        @SequenceGenerator 어노테이션이 필요하다.
        - @GeneratedValue(strategy = GenerationType.TABLE)
        키를 생성하는 테이블을 사용하는 방법으로 Sequence와 유사하다.
        @TableGenerator 어노테이션이 필요하다.
        - @GeneratedValue(strategy = GenerationType.AUTO)
        기본 설정 값으로 각 데이터베이스에 따라 기본키를 자동으로 생성
    */
    private Long id;

    @Setter//수정이 필요한 부분만 setter 어너테이션을 붙인다. / id 등에 사용자나 개발자가 수정할 수 있는 것을 막기 위해 각각 설정했다.
    @Column(nullable = false)
    /* nullable은 @Column의 속성 중 하나이며, 기본값은 true이다.

    * DDL 생성 시 not null로 생성된다.
    * @NotNull : null이 들어갈 경우 예외를 발생시킨다.
    * nullable의 경우 NotNull과 다르게 null을 넣은 엔티티를 생성해도 개발에서는 오류를 발생하지 않으나,
    * db에서 예외를 발생시킨다. */
    private String title;
    @Setter
    @Column(nullable = false, length = 10000)//length를 통해 데이터 크기를 지정한다.
    private String content;

    @Setter
    private String hashtag;

    /*
    * - OrderBy("id") - 정렬을 하고 정렬 기준은 id이다.
    * - SET을 사용하는 것은 게시글에 영동된 댓글은 중복을 허용하지 않고 모아서 리스트로 보겠다는 의미
    * - OneToMany 연관관계를 매핑해 준다.
    * - mappedBy : 자신이 이 연관관계의 주인이 아님을 설정한다.
    * (일반적으로 외래키를 가진 객체를 주인으로 정의하는 것이 좋다.
    * 보통 N:1 에서 N이 외래키를 가진다.)
    * 작성하지 않을 경우 임의로 두테이블을 합친 한 테이블을 생성한다.
    * - cascade : 부모 엔티티가 삭제될 때 자식 엔티티도 삭제
    * CascadeType.ALL: 모든 Cascade를 적용
    * CascadeType.PERSIST: 엔티티를 영속화할 때, 연관된 엔티티도 함께 유지
    * CascadeType.MERGE: 엔티티 상태를 병합(Merge)할 때, 연관된 엔티티도 모두 병합
    * CascadeType.REMOVE: 엔티티를 제거할 때, 연관된 엔티티도 모두 제거
    * CascadeType.DETACH: 부모 엔티티를 detach() 수행하면, 연관 엔티티도 detach()상태가 되어 변경 사항 반영 X
    * CascadeType.REFRESH: 상위 엔티티를 새로고침(Refresh)할 때, 연관된 엔티티도 모두 새로고침
    */
    @ToString.Exclude//룸복에서 ToString할때 해당 필드를 제외한다.
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    /*
    //자동으로 생성되게 설정한다. JPA Auditing
    @CreatedDate//엔티티가 생성되어 저장될 때, 시간이 자동으로 저장된다.
    @Column(nullable = false)
    private LocalDateTime createdAt;//생성일시
    @CreatedBy//엔티티가 생성되어 저장될 때, 유저의 id를 자동으로 저장된
    @Column(nullable = false, length = 100)
    private String createdBy;//생성자

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;//수정일시
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy;//수정자
    */

    //기본 생성자
    protected Article(){}

    //자동으로 생성되는 컬럼은 제외
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    /* 동일한 게시글인지 확인하기 위한 설정 ctrl+n -> equals() and hashcode() 를 통해서 생성한다.
    * 보통은 룸복을 이용해 비교하나, id가 동일 여부를 통해 동일한 게시글인지 확인이 가능하여
    * 개별적으로 작성한다.

    * 아직 영속성이 등록되지 않은 id값은 null 이므로, null인지 확인한다.
    * (제목, 내용, 해시태그 내용이 같아도 id가 다를 경우 다른 글로 본다.)

    * 영속성 - 데이터를 생성한 프로그램의 실행이 종료되더라도 사라지지 않는 데이터의 특성
    * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
