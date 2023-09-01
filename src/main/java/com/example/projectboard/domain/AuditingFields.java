package com.example.projectboard.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@MappedSuperclass
/* 공통 매핑 정보가 필요할 때, 부모 클래스에 선언하고 속성만 상속 받아서 사용하고 싶을 때 */
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingFields {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @CreatedDate
    @Column(nullable = false, updatable = false)//업데이트 불가
    private LocalDateTime createdAt;
    @CreatedBy
    @Column(nullable = false, updatable = false, length = 100)
    private String createdBy;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime modifiedAt;
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy;

}
