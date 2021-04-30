package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.repository.KomentarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;

@Service
public class KomentarServiceImpl implements KomentarService {
    @Autowired
    private KomentarRepository komentarRepository;


    @Override
    public Iterable<Komentar> getListKomentar() {
        return komentarRepository.findAll();
    }

    @Override
    public Komentar getKomentar(int id) {
        return komentarRepository.findById(id);
    }

    @Override
    public Komentar createKomentar(Komentar komentar) {
        komentarRepository.save(komentar);
        return komentar;
    }

    @Override
    public int deleteKomentar(int id) {
        Komentar komentar = komentarRepository.findById(id);
        if (komentar != null) {
            komentarRepository.deleteById(id);
            return 1;
        }
        return -1;
    }




}
