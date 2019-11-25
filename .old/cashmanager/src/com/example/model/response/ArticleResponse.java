package com.example.model.response;

import com.example.model.Article;

public class ArticleResponse implements Response {
    private Article article;
    public ArticleResponse(Article article) {
        this.article = article;
    }
    public ArticleResponse() {}

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    @Override
    public String getResponse() {
        if (this.article instanceof Article)
            return "ARTICLE "+article.stringify()+"\n";
        else
            return "KO\n";
    }
}
