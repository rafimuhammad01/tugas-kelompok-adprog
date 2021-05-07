package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.repository.TugasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.mockito.Mockito.lenient;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TugasServiceImplTest {
    @Mock
    private TugasRepository tugasRepository;

    @InjectMocks
    private TugasServiceImpl tugasService;

    private Tugas tugas;

    @BeforeEach
    public void setUp() {
        tugas = new Tugas();
        tugas.setId(20);
        tugas.setJudul("Lab01");
        tugas.setDeadline(Timestamp.valueOf("2021-09-26 09:00:00"));
        tugas.setDeskripsi("nondescript");
        tugas.setLink("nolink.com");
    }


    @Test
    void TestGetListTugas() {
        Iterable<Tugas> listTugas = tugasRepository.findAll();
        lenient().when(tugasService.getListTugas()).thenReturn(listTugas);
        Iterable<Tugas> listTugasResult = tugasService.getListTugas();
        Assertions.assertIterableEquals(listTugas, listTugasResult);
    }

    @Test
    void TestCreateTugas() {
        lenient().when(tugasService.createTugas(tugas)).thenReturn(tugas);
        Tugas resultTugas = tugasService.createTugas(tugas);
        Assertions.assertEquals(tugas.getId(), resultTugas.getId());
    }

    @Test
    void TestGetTugas() {
        lenient().when(tugasService.getTugas(tugas.getId())).thenReturn(tugas);
        Tugas resultTugas = tugasService.getTugas(tugas.getId());
        Assertions.assertEquals(tugas.getId(), resultTugas.getId());
    }

    @Test
    void TestUpdateTugas() {
        tugasService.createTugas(tugas);
        String judul = "Lab10";
        Timestamp deadline = Timestamp.valueOf("2021-09-26 21:00:00");
        String deskripsi = "have descript";
        String link = "havelink.com";

        tugas.setJudul(judul);
        tugas.setDeskripsi(deskripsi);
        tugas.setDeadline(deadline);
        tugas.setLink(link);

        Tugas expectedTugas = tugas;
        expectedTugas.setJudul(judul);
        expectedTugas.setDeskripsi(deskripsi);
        expectedTugas.setDeadline(deadline);
        expectedTugas.setLink(link);

        Tugas resultTugas = tugasService.updateTugas(tugas.getId(), tugas);
        Assertions.assertEquals(expectedTugas.getJudul(), resultTugas.getJudul());
        Assertions.assertEquals(expectedTugas.getDeadline(), resultTugas.getDeadline());
        Assertions.assertEquals(expectedTugas.getDeskripsi(), resultTugas.getDeskripsi());
        Assertions.assertEquals(expectedTugas.getLink(), resultTugas.getLink());
    }

    @Test
    void TestdeleteTugas() {
        tugasService.createTugas(tugas);
        tugasService.deleteTugas(tugas.getId());
        Assertions.assertEquals(null, tugasService.getTugas(tugas.getId()));
    }
}