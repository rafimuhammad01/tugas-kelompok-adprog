package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Matkul;

public interface MatkulService {
    Iterable<Matkul> getListMatkul();

    Matkul createMatkul(Matkul matkul);

    Matkul getMatkul(String kodeMatkul);

    Matkul updateMatkul(String kodeMatkul, Matkul matkul);

    void deleteMatkul(String kodeMatkul);
}