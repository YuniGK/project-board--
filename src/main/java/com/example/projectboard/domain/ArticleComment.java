package com.example.projectboard.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)//기본 생성자 생성 //생성자 권한은 protected 이다.
//application.yaml에서 test.database.replace: none 추가한 내용과 위 내용이 동일하다.
public class ArticleComment extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    /*
    - 연관관계를 매핑해 준다.

    ManyToOne : 다대일 ( N : 1 ) - 단방향은 한 쪽의 엔티티가 상대 엔티티를 참조하고 있는 상태
    OneToMany : 일대다 ( 1 : N ) -
    OneToOne : 일대일 ( 1 : 1 ) - 외래 키를 갖고 있는 테이블을 주 테이블
    ManyToMany : 다대다 ( N : N ) - 실무 사용 금지 ×

    - 연관관계의 주인 ( Owner )
    객체를 양방향 연관관계로 만들면 연관관계의 주인을 정해야 합니다.
    연관관계를 갖는 두 테이블에서 외래키를 갖게되는 테이블이 연관관계의 주인이 됩니다.
    연관관계의 주인만이 외래 키를 관리(등록, 수정, 삭제) 할 수 있고, 주인이 아닌 엔티티는 읽기만 할 수 있습니다.
    */
    //optional = false - null이 아니다.
    private Article article;//게시글

    @Setter @Column(nullable = false, length = 500)
    private String content;

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }
    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
