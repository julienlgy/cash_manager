package com.example.session;

import com.example.factory.ArticleGetter;
import com.example.model.Article;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class CartSyncHandler {
    private ArrayList<Article> articleList;
    public CartSyncHandler() {
        this.articleList = new ArrayList<Article>();
    }

    public Boolean add_art(String article) { return add_art(Article.parse(article)); }
    public Boolean add_art(Article a){
        return articleList.add(a);
    }

    public Boolean rem_art(String article) { return rem_art(Article.parse(article)); }
    public Boolean rem_art(Article a ) {
        return articleList.remove(a);
    }

    public Article get_art(String articleId) throws IOException, JSONException {
        return ArticleGetter.getArticleById(articleId);
    }
}
