package com.c2.sisteminformasitugas.repository;

import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.model.Tugas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface KomentarRepository  extends JpaRepository<Komentar, Integer> {
    Komentar findById(int id);
    Iterable<Komentar> findByTugas(Tugas tugas);

    @Transactional
    @Modifying
    @Query("delete from Komentar k where k.id=:id")
    void deleteKomentarCustom(@Param("id") int id);
}
