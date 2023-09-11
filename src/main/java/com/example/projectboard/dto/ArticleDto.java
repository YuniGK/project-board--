package com.example.projectboard.dto;

import com.example.projectboard.domain.Article;

import java.io.Serializable;
import java.time.LocalDateTime;

/*
- DTO(Data Transfer Object)란 계층간 데이터 교환을 위해 사용하는 객체
- Entity 클래스를 기준으로 테이블이 생성되고 스키마가 변경 / DB 저장할 때는 Dto ->Entity
(Entity는 Setter를 만들면 안된다.)
    -> Entity 클래스에서 필요한 데이터만 선택적으로 DTO에 담아서 생성해 사용함으로써, Entitiy 클래스를 감추며 보호
- DAO(Repository) 서버와 데이터베이스 사이 연결고리
- toEntity() Dto -> Entity 변환
*/
public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Article toEntity() {
        return Article.of(
                userAccountDto.toEntity(),
                title,
                content,
                hashtag
        );
    }

}
