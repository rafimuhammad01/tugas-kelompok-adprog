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
        
    }

    @Test
    void TestGetTugas() {
    }

    @Test
    void TestUpdateTugas() {
    }

    @Test
    void TestdeleteTugas() {
    }
}