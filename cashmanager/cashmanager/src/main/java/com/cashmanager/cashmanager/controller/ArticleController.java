package com.cashmanager.cashmanager.controller;

import com.cashmanager.cashmanager.exception.ResourceNotFoundException;
import com.cashmanager.cashmanager.model.Article;
import com.cashmanager.cashmanager.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/articles")
    public List<Article> getAllArticles(){
        return articleRepository.findAll();
    }

    @PostMapping("/articles")
    public Article createArticle(@Valid @RequestBody Article article){
        return articleRepository.save(article);
    }

    @GetMapping("/articles/{id]")
    public Article updateArticle(@PathVariable(value="id") Long articleId){
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));
    }

    @PutMapping("/aricles/{id]")
    public Article updateArticle(@PathVariable(value="id") Long articleId, @Valid @RequestBody Article articleDetails){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article","Id", articleId));

        article.setArticleName(articleDetails.getArticleName());
        article.setDescription(articleDetails.getDescription());
        article.setImage(articleDetails.getImage());
        article.setPrix(articleDetails.getPrix());

        Article updatedArticle = articleRepository.save(article);

        return updatedArticle;

    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable(value = "id") Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

        articleRepository.delete(article);

        return ResponseEntity.ok().build();
    }
}
