package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Tugas;

import java.io.IOException;

public interface TugasService {
    Iterable<Tugas> getListTugas(String kodeMatkul);

    Tugas createTugas(Tugas tugas) throws IOException, InterruptedException;

    Tugas getTugas(int id);

    Tugas updateTugas(int id, Tugas tugas);

    void deleteTugas(int id);
}
