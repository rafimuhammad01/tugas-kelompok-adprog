package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.repository.KomentarRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
class KomentarServiceImplTest {

    @Mock
    private KomentarRepository komentarRepository;

    @InjectMocks
    private KomentarServiceImpl komentarServiceImpl;

    private Komentar komentar;

    @BeforeEach
    public void setUp(){
        komentar = new Komentar();
        komentar.setId(1);
        komentar.setComment("Dummy");
    }


    @Test
    void testGetListKomentar() {
        Iterable<Komentar> komentarList = komentarRepository.findAll();
        lenient().when(komentarServiceImpl.getListKomentar()).thenReturn(komentarList);
        Iterable<Komentar> komentarListResult = komentarServiceImpl.getListKomentar();
        Assertions.assertIterableEquals(komentarList, komentarListResult);
    }

    @Test
    void testGetKomentar() {
        lenient().when(komentarServiceImpl.getKomentar(komentar.getId())).thenReturn(komentar);
        Komentar komentarResult = komentarServiceImpl.getKomentar(komentar.getId());
        Assertions.assertEquals(komentar.getId(), komentarResult.getId());


    }

    @Test
    void testCreateKomentar() {
        lenient().when(komentarServiceImpl.createKomentar(komentar)).thenReturn(komentar);
        Komentar komentarResult = komentarServiceImpl.createKomentar(komentar);
        Assertions.assertEquals(komentar.getId(), komentarResult.getId());
    }

    @Test
    void testDeleteKomentar() {
        komentarServiceImpl.createKomentar(komentar);
        lenient().when(komentarServiceImpl.getKomentar(komentar.getId())).thenReturn(komentar);
        komentarServiceImpl.deleteKomentar(komentar.getId());
        lenient().when(komentarServiceImpl.getKomentar(komentar.getId())).thenReturn(null);
        assertEquals(null, komentarServiceImpl.getKomentar(komentar.getId()));
    }
}