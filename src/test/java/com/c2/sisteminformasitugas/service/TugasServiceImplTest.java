package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.util.Helper;
import com.c2.sisteminformasitugas.repository.KomentarRepository;
import com.c2.sisteminformasitugas.repository.TugasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TugasServiceImplTest {

    @Mock
    private MatkulServiceImp matkulServiceImpl;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private KomentarServiceImpl komentarServiceImpl;


    @Mock
    private TugasServiceImpl tugasService;

    @Mock
    private TugasRepository tugasRepository;

    @Mock
    private MatkulRepository matkulRepository;

    @Mock
    private KomentarRepository komentarRepository;

    @InjectMocks
    private TugasServiceImpl tugasServiceImpl;

    private Komentar komentar;
    private Tugas tugas;
    private Matkul matkul;
    private User user;

    @BeforeEach
    public void setUp() {

        user = new User();
        user.setNpm("1234");
        user.setAdmin(false);
        user.setEmail("test@gmail.com");
        user.setPassword("test123");

        tugas = new Tugas();
        tugas.setId(1);
        tugas.setJudul("Dummy");
        tugas.setDeadline(new Timestamp(System.currentTimeMillis()));
        tugas.setDeskripsi("Dummy");
        tugas.setLink("Dummy");
        tugas.setMatkul(matkul);
        tugasRepository.save(tugas);

        matkul = new Matkul();
        matkul.setKodeMatkul("123");
        matkul.setNama("Dummy");

        user = new User();
        user.setAdmin(false);
        user.setEmail("dummy@gmail.com");
        user.setPassword("password123");
        user.setNpm("1906350788");


        komentar = new Komentar();
        komentar.setId(1);
        komentar.setComment("Dummy");
        komentar.setAuthor(user);


    }

    @Test
    void TestGetListTugas() {
        Iterable<Tugas> listTugas = tugasRepository.findAll();
        lenient().when(tugasServiceImpl.getListTugas(matkul.getKodeMatkul())).thenReturn(listTugas);
        Iterable<Tugas> listTugasResult = tugasServiceImpl.getListTugas(matkul.getKodeMatkul());
        Assertions.assertIterableEquals(listTugas, listTugasResult);
    }

    @Test
    void TestCreateTugas() {
        lenient().when(tugasServiceImpl.createTugas(tugas)).thenReturn(tugas);
        Tugas resultTugas = tugasServiceImpl.createTugas(tugas);
        Assertions.assertEquals(tugas.getId(), resultTugas.getId());
    }

    @Test
    void TestGetTugas() {
        lenient().when(tugasServiceImpl.getTugas(tugas.getId())).thenReturn(tugas);
        Tugas resultTugas = tugasServiceImpl.getTugas(tugas.getId());
        Assertions.assertEquals(tugas.getId(), resultTugas.getId());
    }

    @Test
    void TestUpdateTugas() throws Exception {
        String judul = "Lab10";
        Timestamp deadline = Timestamp.valueOf("2021-09-26 21:00:00");
        String deskripsi = "have descript";
        String link = "havelink.com";

        tugasServiceImpl.createTugas(tugas);
        matkulServiceImpl.createMatkul(matkul);
        lenient().when(tugasServiceImpl.getTugas(tugas.getId())).thenReturn(tugas);

        tugas.setJudul(judul);
        tugas.setDeskripsi(deskripsi);
        tugas.setDeadline(deadline);
        tugas.setLink(link);

        Tugas expectedTugas = tugas;
        expectedTugas.setJudul(judul);
        expectedTugas.setDeskripsi(deskripsi);
        expectedTugas.setDeadline(deadline);
        expectedTugas.setLink(link);

        Tugas resultTugas = tugasServiceImpl.updateTugas(tugas.getId(), tugas);
        Assertions.assertEquals(expectedTugas.getJudul(), resultTugas.getJudul());
        Assertions.assertEquals(expectedTugas.getDeadline(), resultTugas.getDeadline());
        Assertions.assertEquals(expectedTugas.getDeskripsi(), resultTugas.getDeskripsi());
        Assertions.assertEquals(expectedTugas.getLink(), resultTugas.getLink());
    }

    @Test
    void TestdeleteTugas() {

        tugasServiceImpl.createTugas(tugas);
        tugasServiceImpl.deleteTugas(tugas.getId());
        Assertions.assertEquals(null, tugasServiceImpl.getTugas(tugas.getId()));
    }

}