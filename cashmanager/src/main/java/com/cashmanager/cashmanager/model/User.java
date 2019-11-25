package com.cashmanager.cashmanager.model;

import javax.persistence.*;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Entity
@Table(name = "UTILISATEUR")
@XmlRootElement(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "USER_NAME", unique=true, insertable=true, updatable=true, nullable=false)
    private String user_name;

    @Column(name = "USER_PASSWORD", insertable=true, updatable=true, nullable=false)
    private String password;

   /* @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Panier> paniers; */

    public User() {
        super();
    }


    public User(Long id, String user_name) {
        this.id = id;
        this.user_name = user_name;
    }

    public User(String user_name) {
        this.user_name = user_name;
    }

    public User(Long user_id, String user_name, String password) {
        this.id = user_id;
        this.user_name = user_name;
        this.password = password;
    }

    public User(String user_name, String password){
        this.user_name = user_name;
        this.password = password;
        /*this.paniers = Stream.of(paniers).collect(Collectors.toSet());
        this.paniers.forEach(x -> x.setUser(this));*/
    }
    public Long getId() {
        return id;
    }

    @XmlElement
    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return user_name;
    }
    @XmlElement
    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User [id=" + id + ", login=" + user_name + ", password=XXXXXXX]";
    }

}
