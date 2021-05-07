package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.service.TugasService;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatkulServiceImp implements MatkulService {
    @Autowired
    private MatkulRepository matkulRepository;
    @Autowired
    private TugasService tugasService;
    @Autowired
    private ToDoListService todolistService;

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

    public Tugas createNewTugas(Tugas tugas){
        this.notifySubscriber(tugas);
        return tugasService.createTugas(tugas);
    }

    public void notifySubscriber(Tugas tugas){
        Matkul matkul = tugas.getMatkul();
        for(User eachUser : matkul.getSubscribers()){
            todolistService.createToDoList(tugas,eachUser);
        }

    }
}