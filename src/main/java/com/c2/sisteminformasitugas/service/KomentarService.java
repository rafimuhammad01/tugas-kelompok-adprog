package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Komentar;

public interface KomentarService {
    Iterable<Komentar> getListKomentar();
    Komentar getKomentar(int id);
    Komentar createKomentar(Komentar komentar);
    int deleteKomentar(int id);
}
