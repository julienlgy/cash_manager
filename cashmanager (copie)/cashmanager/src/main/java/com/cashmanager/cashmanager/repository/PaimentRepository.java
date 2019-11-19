package com.cashmanager.cashmanager.repository;

import com.cashmanager.cashmanager.model.Paiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaimentRepository extends JpaRepository<Paiment, Long> {
}
