package com.c2.sisteminformasitugas.service;


import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.repository.TugasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TugasServiceImpl implements TugasService{
    @Autowired
    private TugasRepository tugasRepository;
    @Autowired
    private MatkulRepository matkulRepository;

    @Override
    public Iterable<Tugas> getListTugas(String kodeMatkul) {
        Matkul matkul = matkulRepository.findByKodeMatkul(kodeMatkul);
        return tugasRepository.findByMatkul(matkul);
    }

    @Override
    public Tugas createTugas(Tugas tugas){
        tugasRepository.save(tugas);
        return tugas;
    }

    @Override
    public Tugas getTugas(int id){
        return tugasRepository.findById(id);
    }

    @Override
    public Tugas updateTugas(int id, Tugas tugas){
        Tugas tugasFound = tugasRepository.findById(id);
        tugas.setId(id);
        tugas.setKomentar(tugasFound.getKomentar());
        tugas.setMatkul(tugasFound.getMatkul());
        tugas.setKomentar(new ArrayList<>());
        tugasRepository.save(tugas);
        return tugas;
    }

    @Override
    public void deleteTugas(int id) {
        Tugas tugas = this.getTugas(id);
        tugasRepository.delete(tugas);
    }
}
