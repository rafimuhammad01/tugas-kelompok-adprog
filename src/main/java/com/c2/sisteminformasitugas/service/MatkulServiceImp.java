package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.DTO.ListKodeMatkulDTO;
import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatkulServiceImp implements MatkulService {
    @Autowired
    private MatkulRepository matkulRepository;
    @Autowired
    private TugasService tugasService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<Matkul> getListMatkul() {
        return matkulRepository.findAll();
    }

    @Override
    public Matkul createMatkul(Matkul matkul) {
        matkul.setSubscribers(new ArrayList<>());
        matkul.setTugas(new ArrayList<>());
        matkulRepository.save(matkul);
        return  matkul;
    }

    @Override
    public Matkul getMatkul(String kodeMatkul) {
        return matkulRepository.findByKodeMatkul(kodeMatkul);
    }

    @Override
    public Matkul updateMatkul(String kodeMatkul, Matkul matkul) {
        Matkul matkulFound = matkulRepository.findByKodeMatkul(kodeMatkul);
        matkul.setKodeMatkul(kodeMatkul);
        matkul.setTugas(matkulFound.getTugas());
        matkul.setSubscribers(matkulFound.getSubscribers());
        matkulRepository.save(matkul);
        return matkul;
    }

    @Override
    public void deleteMatkul(String kodeMatkul) {
        Matkul matkul = this.getMatkul(kodeMatkul);
        matkulRepository.delete(matkul);
    }

    @Override
    public User subscribeToMatkul(User user, ListKodeMatkulDTO listKodeMatkulDTO) {
        List<String> kodeMatkuls = listKodeMatkulDTO.getKodeMatkuls();
        for (String kodeMatkul:kodeMatkuls) {
            Matkul matkul = matkulRepository.findByKodeMatkul(kodeMatkul);
            if (matkul != null && !user.getMatkulList().contains(matkul)) {
                matkul.getSubscribers().add(user);
                matkulRepository.save(matkul);
                user.getMatkulList().add(matkul);
                userRepository.save(user);
            }
        }
        return user;
    }

    @Override
    public User unsubscribeToMatkul(User user, ListKodeMatkulDTO listKodeMatkulDTO) {
        List<String> kodeMatkuls = listKodeMatkulDTO.getKodeMatkuls();
        for (String kodeMatkul:kodeMatkuls) {
            Matkul matkul = matkulRepository.findByKodeMatkul(kodeMatkul);
            if (matkul != null && user.getMatkulList().contains(matkul)) {
                matkul.getSubscribers().remove(user);
                matkulRepository.save(matkul);
                user.getMatkulList().remove(matkul);
                userRepository.save(user);
            }
        }
        return user;
    }
}