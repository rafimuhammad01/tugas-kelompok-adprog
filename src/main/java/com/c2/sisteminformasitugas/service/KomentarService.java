package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;

public interface KomentarService {
    Iterable<Komentar> getListKomentar(Tugas tugasID);
    Komentar createKomentar(Komentar komentar, User user, Tugas tugasID);
    int deleteKomentar(int id);
}
