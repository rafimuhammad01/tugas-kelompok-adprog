package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.KomentarRepository;
import com.c2.sisteminformasitugas.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

@Service
public class KomentarServiceImpl implements KomentarService {
    @Autowired
    private KomentarRepository komentarRepository;

    @Override
    public Iterable<Komentar> getListKomentar(Tugas tugas) {
        return komentarRepository.findByTugas(tugas);
    }

    @Override
    public Komentar createKomentar(Komentar komentar, User user, Tugas tugas) {
        // Set tugas and user for tugas_id and author field in Komentar table
        komentar.setAuthor(user);
        komentar.setTugas(tugas);

        Timestamp now = new Timestamp(System.currentTimeMillis());

        // condition if deadline haven't passed
        if (tugas.getDeadline().compareTo(now) >= 0) {
            return komentarRepository.save(komentar);
        }
        return null;
    }

    @Override
    public int deleteKomentar(int id) {
        Komentar komentar = komentarRepository.findById(id);
        if (komentar != null) {
            komentarRepository.deleteKomentarCustom(id);
            return Helper.COMMENT_DELETED;
        }
        return Helper.COMMENT_NOT_FOUND;
    }

}
