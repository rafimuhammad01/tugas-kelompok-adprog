package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatkulServiceImp implements MatkulService {
    @Autowired
    private MatkulRepository matkulRepository;

    @Override
    public Iterable<Matkul> getListMatkul() {
        return matkulRepository.findAll();
    }

    @Override
    public Matkul createMatkul(Matkul matkul) {
        matkulRepository.save(matkul);
        return  matkul;
    }

    @Override
    public Matkul getMatkul(String kodeMatkul) {
        return matkulRepository.findByKodeMatkul(kodeMatkul);
    }

    @Override
    public Matkul updateMatkul(String kodeMatkul, Matkul matkul) {
        matkulRepository.save(matkul);
        return matkul;
    }

    @Override
    public void deleteMatkul(String kodeMatkul) {
        Matkul matkul = this.getMatkul(kodeMatkul);
        matkulRepository.delete(matkul);
    }
}