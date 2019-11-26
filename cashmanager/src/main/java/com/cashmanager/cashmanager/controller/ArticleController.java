package com.cashmanager.cashmanager.controller;

import com.cashmanager.cashmanager.exception.ResourceNotFoundException;
import com.cashmanager.cashmanager.model.Article;
import com.cashmanager.cashmanager.repository.ArticleRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1")
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

    @GetMapping("/articles/{id}")
    public ArticleResponse updateArticle(@PathVariable(value="id") Long articleId) throws IOException {
        Article myArt = null;
        ArticleResponse myArtRes = null;
        String urlOpenFactsFood = "https://fr.openfoodfacts.org/api/v0/product/" + articleId + ".json";
        URL obj = new URL(urlOpenFactsFood);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        java.lang.String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);
        in.close();

        System.out.println(response.toString());

        try {
            java.lang.String myImg;
            JSONObject myResponse = new JSONObject((response.toString()));
            JSONObject myProduct = new JSONObject(myResponse.getString("product"));

            if (myProduct.has("image_url")) { myImg = myProduct.getString("image_url"); }
            else {
                if (myProduct.has("image_nutrition_url"))
                    myImg = myProduct.getString("image_nutrition_url");
                else
                    myImg = "no image for this article";
            }

            myArt = new Article(
                    articleId,
                    myProduct.getString("product_name"),
                    "",
                    myImg,
                    ArticleController.randomPrice(articleId)
            );

            Article finalMyArt = myArt;
            if (!articleRepository.findById(articleId).isPresent()) {
                System.out.println("ARTICLE NON PRESENT EN BDD");
                Article newArticle = articleRepository.save(myArt);
            } else {
                System.out.println("ARTICLE PRESENT EN BDD");
            }

            myArtRes = new ArticleResponse(true, ArticleController.serializedArticle(myArt));
        } catch (JSONException e) {
            myArtRes = new ArticleResponse(false, "");
            System.out.println(e.getMessage());
        }

        /* **** */

        /*
        * VERIFIER SI article PRESENT DANS BDD
        *   AJOUTER SINON
        *  */
        return myArtRes;
        /*return articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));*/
    }

    @PutMapping("/articles/{id}")
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

    public static float randomPrice(Long articleId) {
        Random random = new Random();
        random.setSeed(articleId);
        float f = random.nextFloat();
        return f;
    }

    public static String serializedArticle(Article article) {
        String serialized = article.getId()
                + "#" + article.getArticleName()
                + "#" + article.getDescription()
                + "#" + article.getImage()
                + "#" + article.getPrix();
        return serialized;
    }

    public class ArticleResponse {
        private boolean status;
        private String article;

        public ArticleResponse(boolean status, String article) {
            this.status = status;
            this.article = article;
        }

        public boolean getStatus() { return this.status; }

        public String getArticle() { return this.article; }
    }
}
