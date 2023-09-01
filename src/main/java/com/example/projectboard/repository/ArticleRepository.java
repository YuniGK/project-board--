package com.example.projectboard.repository;

import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,//Article 필드의 기본 검색 기능 추가
        QuerydslBinderCustomizer<QArticle> {
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true);//검색에서 제외할 수 있도록 설정
        //검색을 원하는 필드를 추가한다.
        bindings.including(root.title
                , root.content
                , root.hashtag
                , root.createdAt
                , root.createdBy
        );
        //검색시 하나만 넘겨준다.
        //bindings.bind(root.title).first((path, value) -> path.eq(value));
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);//대소문자 구분하지 않는다.
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);//대소문자 구분하지 않는다.
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);//대소문자 구분하지 않는다.
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);//동일한 날짜를 찾는다.
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);//대소문자 구분하지 않는다.
    }
}