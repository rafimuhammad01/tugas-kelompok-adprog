package com.c2.sisteminformasitugas.Service;

import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.service.MatkulServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatkulServiceImpTest {
    @Mock
    private MatkulRepository matkulRepository;

    @InjectMocks
    private MatkulServiceImp matkulService;

    private Matkul matkul;
    private Tugas tugas;

    @BeforeEach
    public void setUp() {
        matkul = new Matkul();
        matkul.setKodeMatkul("ADVPROG");
        matkul.setNama("Advanced Programming");


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

//    @Test
//    void testServiceCreateTugas(){
//        tugas = new Tugas();
//        tugas.setId(20);
//        tugas.setJudul("Lab01");
//        tugas.setDeadline(Timestamp.valueOf("2021-09-26 09:00:00"));
//        tugas.setDeskripsi("nondescript");
//        tugas.setLink("nolink.com");
//
//        lenient().when(matkulService.createNewTugas(tugas));
//        Tugas resultTugas = matkulService.createNewTugas(tugas);
//        Assertions.assertEquals(tugas.setId(), resultTugas.setId());
//    }

    @Test
    void testServiceUpdateMatkul() {
        matkulService.createMatkul(matkul);
        String namaMatkul = "ADV125YIHA";
        matkul.setNama(namaMatkul);
        Matkul expectedMatkul = matkul;
        expectedMatkul.setNama(namaMatkul);
        Matkul resultMatkul = matkulService.updateMatkul(matkul.getKodeMatkul(), matkul);
        Assertions.assertEquals(expectedMatkul.getNama(), resultMatkul.getNama());
    }

    @Test
    void testServiceDeleteMataKuliah() {
        matkulService.createMatkul(matkul);

        lenient().when(matkulService.getMatkul(matkul.getKodeMatkul())).thenReturn(matkul);
        matkulService.deleteMatkul(matkul.getKodeMatkul());
        verify(matkulRepository, times(1)).delete(matkul);
    }

}
