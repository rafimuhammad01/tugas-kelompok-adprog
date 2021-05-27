package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;

import java.io.IOException;
import java.util.List;

public interface TugasService {
    Iterable<Tugas> getListTugas(String kodeMatkul);

    Tugas createTugas(Tugas tugas) throws IOException, InterruptedException;

    Tugas getTugas(int id);

    Tugas updateTugas(int id, Tugas tugas);

    void deleteTugas(int id);

    List<Tugas> getAllTugasByUser(User user);
}
