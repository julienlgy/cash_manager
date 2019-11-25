package com.cashmanager.cashmanager.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PAIMENT")
@XmlRootElement(name = "paiment")

public class Paiment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAIMENT_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "TYPE_PAIMENT", unique=false, insertable=true, updatable=true, nullable=false)
    private String type_paiment;

    @Column(name = "TOTAL", unique=false, insertable=true, updatable=true, nullable=false)
    private Long total;


   /* @ManyToOne
    @JoinColumn
    private User user; */

    public Paiment(){
        super();
    }

    public Paiment(String type_paiment, Long total) {
        this.type_paiment = type_paiment;
        this.total = total;
    }

    public Paiment(Long id, String type_paiment, Long total){
        this.id = id;
        this.type_paiment = type_paiment;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public String getType(){ return this.type_paiment; }

    @XmlElement
    public void setType(String type_paiment){ this.type_paiment = type_paiment; }

    public Long getTotal(){ return total; }

    @XmlElement
    public void setTotal(Long total){ this.total = total; }

    /*public User getUser(){return this.user; }

    @XmlElement
    public void setUser(User user){ this.user = user; } */


    @Override
    public String toString() {
        return "Article [id=" + id + "type paiment"+ type_paiment + "total"+ total;
    }
}
