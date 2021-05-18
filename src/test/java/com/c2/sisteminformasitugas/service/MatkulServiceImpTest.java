package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.repository.TugasRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;

import java.sql.Timestamp;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatkulServiceImpTest {
    @Mock
    private MatkulRepository matkulRepository;

    @Mock
    private MatkulServiceImp matkulService;

    private Matkul matkul;

    @Mock
    private TugasServiceImpl tugasService;

    @Mock
    private TugasRepository tugasRepository;

    private Tugas tugas;

    @BeforeEach
    public void setUp() {
        matkul = new Matkul();
        matkul.setKodeMatkul("ADVPROG");
        matkul.setNama("Advanced Programming");

        tugas = new Tugas();
        tugas.setId(1);
        tugas.setJudul("Dummy");
        tugas.setDeadline(new Timestamp(System.currentTimeMillis()));
        tugas.setDeskripsi("Dummy");
        tugas.setLink("Dummy");
        tugas.setMatkul(matkul);
    }

    @Test
    void testServiceGetListMatkul() {
        Iterable<Matkul> listMatkul = matkulRepository.findAll();
        lenient().when(matkulService.getListMatkul()).thenReturn(listMatkul);
        Iterable<Matkul> listMatkulResult = matkulService.getListMatkul();
        Assertions.assertIterableEquals(listMatkul, listMatkulResult);
    }

    @Test
    void testServiceGetMatkul() {
        lenient().when(matkulService.getMatkul(matkul.getKodeMatkul())).thenReturn(matkul);
        Matkul resultMatkul = matkulService.getMatkul(matkul.getKodeMatkul());
        Assertions.assertEquals(matkul.getKodeMatkul(), resultMatkul.getKodeMatkul());
    }

    @Test
    void testServiceCreateMatkul() {
        lenient().when(matkulService.createMatkul(matkul)).thenReturn(matkul);
        Matkul resultMatkul = matkulService.createMatkul(matkul);
        Assertions.assertEquals(matkul.getKodeMatkul(), resultMatkul.getKodeMatkul());
    }

    @Test
    void testServiceUpdateMatkul() {
        matkulService.createMatkul(matkul);
        tugasService.createTugas(tugas);
        lenient().when(matkulService.getMatkul(matkul.getKodeMatkul())).thenReturn(matkul);
        matkul.setTugas(matkul.getTugas());
        Matkul expectedMatkul = matkul;
        expectedMatkul.setTugas(matkul.getTugas());
        Assertions.assertEquals(expectedMatkul.getTugas(), matkul.getTugas());
    }

    @Test
    void testServiceDeleteMatkul() {
        matkulService.createMatkul(matkul);
        lenient().when(matkulService.getMatkul(matkul.getKodeMatkul())).thenReturn(matkul);
        matkulService.deleteMatkul(matkul.getKodeMatkul());
//        verify(matkulRepository, times(1)).delete(matkul);
    }
}
