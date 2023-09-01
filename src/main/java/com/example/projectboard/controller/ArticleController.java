package com.example.projectboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    @GetMapping
    public String articles(ModelMap map){
        map.addAttribute("articles", List.of());
        return "articles/index";
    }

    @GetMapping("/{articleId}")
    /* 요청 URI 매핑에서 템플릿 변수를 처리 하고 이를 메서드 매개변수로 설정할 수 있습니다. */
    public String articles(@PathVariable long articleId, ModelMap map){
        map.addAttribute("article", "article");//TODO: 구현시 실제 데이터를 넣어줘야한다.
        map.addAttribute("articleComments", List.of());

        return "articles/detail";
    }
}
