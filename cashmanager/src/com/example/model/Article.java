package com.example.model;

/**
 * jlegay 2019
 * #EPITECH - Article model server side {JAVA}
 */
public class Article {
    private String id;
    private String nom;
    private String description;
    private String img;
    private Float prix;
    public Article(String id,String nom,String description,String img, Float prix) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.img = img;
        this.prix = prix;
    }

    public String stringify() {
        return this.id+"#"+this.nom+"#"+this.description+"#"+this.img+"#"+this.prix.toString();
    }
    static Article parse(String stringify) {
        String[] astr = stringify.split("#");
        return new Article(
                astr[0],
                astr[1],
                astr[2],
                astr[3],
                Float.parseFloat(astr[4])
        );
    }
}
