package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.repository.TugasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatkulServiceImp implements MatkulService {
    @Autowired
    private MatkulRepository matkulRepository;

    @Autowired
    private TugasRepository tugasRepository;

    @Autowired
    private TugasService tugasService;

    @Autowired
    private Tugas tugasMatkul;

    @Override
    public Iterable<Matkul> getListMatkul() {
        return matkulRepository.findAll();
    }

    @Override
    public Matkul createMatkul(Matkul matkul) {
        System.out.println("Matkul service : \n" + matkul);
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

    @Override
    public void addSubscriber(String kodeMatkul, User user) {
        getMatkul(kodeMatkul).getSubscribers().add(user);
    }

//    public void createTugas(Tugas tugas){
//        tugasMatkul = tugasService.createTugas(tugas);
//        notifySubscribers(tugasMatkul);
//    }

//    public void createTodoList()

//    public void notifySubscribers(Tugas tugas) {
//        Iterate through Users in matkul
//        Matkul matkul = getMatkul(tugas.getMatkul());
//
//    }

}