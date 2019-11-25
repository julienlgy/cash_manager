package com.cashmanager.cashmanager.repository;

import com.cashmanager.cashmanager.model.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {

}

