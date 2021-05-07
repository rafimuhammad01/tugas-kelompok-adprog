package com.c2.sisteminformasitugas.repository;

import com.c2.sisteminformasitugas.model.Komentar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KomentarRepository  extends JpaRepository<Komentar, Integer> {
    Komentar findById(int id);
}
