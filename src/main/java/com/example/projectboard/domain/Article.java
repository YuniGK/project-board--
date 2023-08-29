package com.example.projectboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})/* 테이블 지정 - index를 여러개 추가하는 방법
    / 테이블에 대한 데이터 검색 작업의 속도를 향상 시키는 데이터 구조 */
@Entity
public class Article {
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
}
