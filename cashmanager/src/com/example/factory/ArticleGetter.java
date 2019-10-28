package com.example.factory;

import com.example.model.Article;

import java.util.Random;

/**
 * jlegay 2019
 * Simple class who call an API and return infromations about the price
 * /!\ due to the difficulty to find the price by a barcode, the price is randomized by the id.
 */
public class ArticleGetter {
    private static String API = "https://apiurl.com/api/{{id}}.json";
    public static Article getArticleById(String articleId) {
        String urlToCall = API.replace("{{id}}", articleId);
        // Api call.
        return new Article(
                articleId,
                "Article non trouvé",
                "Cet article n'a pas forcément de description",
                "noimg",
                randomizePrice(articleId)
        );
    }
    public static Float randomizePrice(String articleId) {
        Random random = new Random();
        random.setSeed(Long.parseLong(articleId));
        Float f = random.nextFloat();
        return f;
    }
}
