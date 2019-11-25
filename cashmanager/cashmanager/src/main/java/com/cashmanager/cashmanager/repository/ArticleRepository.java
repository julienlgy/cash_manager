package com.cashmanager.cashmanager.repository;

import com.cashmanager.cashmanager.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

}


