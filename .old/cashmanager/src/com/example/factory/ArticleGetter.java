package com.example.factory;

import com.example.model.Article;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.Buffer;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * jlegay 2019
 * Simple class who call an API and return infromations about the price
 * /!\ due to the difficulty to find the price by a barcode, the price is randomized by the id.
 */
public class ArticleGetter {
    //private static String API = "https://apiurl.com/api/{{id}}.json";
    private static String API = "https://fr.openfoodfacts.org/api/v0/product/{{id}}.json";

    public static Article getArticleById(String articleId) throws IOException {

        Article myArt = null;
        String urlToCall = API.replace("{{id}}", articleId);

        URL obj = new URL(urlToCall);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + urlToCall);
        System.out.print("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

        try {
            String myImg;
            JSONObject myResponse = new JSONObject(response.toString());
            JSONObject myProduct = new JSONObject(myResponse.getString("product"));

            if (myProduct.has("image_url")) { myImg = myProduct.getString("image_url"); }
            else {
                if (myProduct.has("image_nutrition_url")) {
                    myImg = myProduct.getString("image_nutrition_url");
                } else {
                    myImg = "no image for this article";
                }
            }

            myArt = new Article(
                    articleId,
                    myProduct.getString("product_name"),
                    "",
                    myImg,
                    randomizePrice(articleId)
            );
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        return myArt;
    }

    public static Float randomizePrice(String articleId) {
        Random random = new Random();
        random.setSeed(Long.parseLong(articleId));
        Float f = random.nextFloat();
        return f;
    }
}