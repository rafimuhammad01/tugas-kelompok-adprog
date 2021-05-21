package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.KomentarRepository;
import com.c2.sisteminformasitugas.util.Helper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
class KomentarServiceImplTest {

    @Mock
    private KomentarRepository komentarRepository;

    @InjectMocks
    private KomentarServiceImpl komentarServiceImpl;

    private Komentar komentar;
    private Tugas tugas;
    private Matkul matkul;
    private User user;

    @BeforeEach
    public void setUp(){
        komentar = new Komentar();
        komentar.setId(1);
        komentar.setComment("Dummy");

        matkul = new Matkul();
        matkul.setKodeMatkul("123");
        matkul.setNama("Dummy");


        tugas = new Tugas();
        tugas.setId(1);
        tugas.setJudul("Dummy");
        tugas.setDeadline(new Timestamp(System.currentTimeMillis()));
        tugas.setDeskripsi("Dummy");
        tugas.setLink("Dummy");
        tugas.setMatkul(matkul);

        user = new User();
        user.setNpm("1234");
        user.setEmail("test@gmail.com");
        user.setPassword("test123");

    }


    @Test
    void testGetListKomentar() {
        Iterable<Komentar> komentarList = komentarRepository.findAll();
        lenient().when(komentarServiceImpl.getListKomentar(tugas)).thenReturn(komentarList);
        Iterable<Komentar> komentarListResult = komentarServiceImpl.getListKomentar(tugas);
        Assertions.assertIterableEquals(komentarList, komentarListResult);
    }



    @Test
    void testCreateKomentarAfterDeadline() {
        lenient().when(komentarRepository.save(komentar)).thenReturn(komentar);
        tugas.setDeadline(Timestamp.valueOf("2000-09-01 09:01:15"));
        Komentar komentarResult = komentarServiceImpl.createKomentar(komentar, user, tugas);
        Assertions.assertEquals(null, komentarResult);
    }

    @Test
    void testCreateKomentarBeforeDeadline() {
        String str="2022-09-01 09:01:15";
        Timestamp timestamp= Timestamp.valueOf(str);
        tugas.setDeadline(timestamp);
        lenient().when(komentarRepository.save(komentar)).thenReturn(komentar);
        Komentar komentarResult = komentarServiceImpl.createKomentar(komentar, user, tugas);
        Assertions.assertEquals(komentar.getId(), komentarResult.getId());
    }


    @Test
    void testDeleteKomentarSuccess() {
        komentarServiceImpl.createKomentar(komentar, user, tugas);
        lenient().when(komentarRepository.findById(komentar.getId())).thenReturn(komentar);
        int deleteResult = komentarServiceImpl.deleteKomentar(komentar.getId());
        assertEquals(Helper.COMMENT_DELETED, deleteResult);
    }

    @Test
    void testDeleteKomentarNotFound() {
        komentarServiceImpl.createKomentar(komentar, user, tugas);
        lenient().when(komentarRepository.findById(komentar.getId())).thenReturn(null);
        int deleteResult = komentarServiceImpl.deleteKomentar(komentar.getId());
        assertEquals(Helper.COMMENT_NOT_FOUND, deleteResult);
    }


}
