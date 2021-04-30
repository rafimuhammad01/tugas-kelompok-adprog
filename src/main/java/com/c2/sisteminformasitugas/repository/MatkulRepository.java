package com.c2.sisteminformasitugas.repository;

import com.c2.sisteminformasitugas.model.Matkul;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatkulRepository extends JpaRepository<Matkul, String> {
    Matkul findByKodeMatkul(String kodeMatkul);
}