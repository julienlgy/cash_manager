package com.cashmanager.cashmanager.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@Entity
@Table(name = "PANIER")
@XmlRootElement(name = "panier")
public class Panier{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PANIER_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "IS_PAID", unique=false, insertable=true, updatable=true, nullable=false)
    private boolean is_paid;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "ARTICLE_IN_PANIER", joinColumns = @JoinColumn(name = "PANIER_ID"), inverseJoinColumns = @JoinColumn(name = "ARTICLE_ID"))
    private List<Article> articles = new ArrayList<Article>();

   /* @ManyToOne
    @JoinColumn
    private User user; */

    public Panier(){
        super();
    }

    public Panier(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public Panier(Long id, boolean is_paid){
        this.id = id;
        this.is_paid = is_paid;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public boolean getBoolean(){ return this.is_paid; }

    @XmlElement
    public void setBoolean(boolean is_paid){ this.is_paid = is_paid; }

    /*public User getUser(){return this.user; }

    @XmlElement
    public void setUser(User user){ this.user = user; } */

    public boolean setArticle(Article article) {
        if (this.articles.add(article)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteArticle(Article article) {
        System.out.println(" --> " + this.articles + " ------> " + this.articles.contains(article));
        this.articles.remove(article);

        return true;
    }

    public void logArticles(){
        Iterator anIterator=this.articles.iterator();
        while (anIterator.hasNext())
        {
            Object elem=anIterator.next();
            System.out.println(elem);
        }
    }

    @Override
    public String toString() {
        return "Article [id=" + id + "paiment"+ is_paid;
    }
}
