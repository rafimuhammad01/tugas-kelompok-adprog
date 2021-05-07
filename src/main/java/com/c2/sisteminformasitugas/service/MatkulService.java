package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;

public interface MatkulService {
    Iterable<Matkul> getListMatkul();

    Matkul createMatkul(Matkul matkul);

    Matkul getMatkul(String kodeMatkul);

    Tugas createNewTugas(Tugas tugas);

    void notifySubscriber(Tugas tugas);

    Matkul updateMatkul(String kodeMatkul, Matkul matkul);

    void deleteMatkul(String kodeMatkul);
}