package com.c2.sisteminformasitugas.service;


import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.repository.TugasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TugasServiceImpl implements TugasService{
    @Autowired
    private TugasRepository tugasRepository;

    @Override
    public Iterable<Tugas> getListTugas() {
        return tugasRepository.findAll();
    }

    //TODO: Get List Tugas dalam sebuah MATA_Kuliah

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
        tugas.setId(id);
        tugasRepository.save(tugas);
        return tugas;
    }

    @Override
    public void deleteTugas(int id) {
        Tugas tugas = this.getTugas(id);
        tugasRepository.delete(tugas);
    }
}
