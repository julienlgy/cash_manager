package com.cashmanager.cashmanager.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@Entity
@Table(name = "ARTICLE")
@XmlRootElement(name = "article")
public class Article{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "ARTICLE_NAME", unique=true, insertable=true, updatable=true, nullable=false)
    private String article_name;

    @Column(name = "DESCRIPTION", insertable=true, updatable=true, nullable=false)
    private String description;

    @Column(name = "IMAGE", insertable=true, updatable=true, nullable=false)
    private String image;

    @Column(name = "PRIX", insertable=true, updatable=true, nullable=false)
    private float prix;

    @ManyToMany(mappedBy = "articles")
    Set<Panier> paniers;


    public Article() {
        super();
    }

    public Article(String article_name, float prix) {
        this.article_name = article_name;
        this.prix = prix;
    }

    public Article(Long id, String article_name) {
        this.id = id;
        this.article_name = article_name;
    }

    public Article(String article_name) {
        this.article_name = article_name;
    }

    public Article(Long id, String article_name, String description, String image, float prix) {
        this.id= id;
        this.article_name=article_name;
        this.description = description;
        this.image = image;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleName() {
        return article_name;
    }
    @XmlElement
    public void setArticleName(String article_name) {
        this.article_name = article_name;
    }

    public String getDescription() {
        return description;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    @XmlElement
    public void setImage(String image) {
        this.image = image;
    }

    public float getPrix() {
        return prix;
    }

    @XmlElement
    public void setPrix(float prix) {
        this.prix = prix;
    }


    @Override
    public String toString() {
        return "Article [id=" + id + ", login=" + article_name + ", description=" + description + ", image=" + image + ",prix=" + prix + ".";
    }
}
