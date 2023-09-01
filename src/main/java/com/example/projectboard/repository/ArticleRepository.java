package com.example.projectboard.repository;

import com.example.projectboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource//repository(저장소) 선언에 대한 어노테이션 - 내부적으로 Rest API가 만들어진다.
public interface ArticleRepository extends JpaRepository<Article, Long> {
}