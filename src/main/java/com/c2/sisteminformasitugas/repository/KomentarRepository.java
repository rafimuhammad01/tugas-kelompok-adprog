package com.c2.sisteminformasitugas.repository;

import com.c2.sisteminformasitugas.model.Komentar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KomentarRepository  extends JpaRepository<Komentar, Integer> {
    Komentar findById(int id);
}
