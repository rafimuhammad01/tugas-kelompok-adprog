package com.c2.sisteminformasitugas.repository;

import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TugasRepository extends JpaRepository<Tugas, Integer> {
    Tugas findById(int id);
    List<Tugas> findByMatkul(Matkul matkul);
}
