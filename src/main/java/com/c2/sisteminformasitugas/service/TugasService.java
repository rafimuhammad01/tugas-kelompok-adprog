package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Tugas;

public interface TugasService {
    Iterable<Tugas> getListTugas();

    //TODO: Get List Tugas dalam sebuah MATA_Kuliah

    Tugas createTugas(Tugas tugas);

    Tugas getTugas(int id);

    Tugas updateTugas(int id, Tugas tugas);

    void deleteTugas(int id);
}
