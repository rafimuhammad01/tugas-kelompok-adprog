package com.c2.sisteminformasitugas.repository;

import com.c2.sisteminformasitugas.model.Tugas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TugasRepository extends JpaRepository<Tugas, String> {
    Tugas findById(int id);
}
