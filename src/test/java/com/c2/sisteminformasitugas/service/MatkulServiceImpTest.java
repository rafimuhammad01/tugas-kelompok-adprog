package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.dto.ListKodeMatkulDTO;
import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.repository.TugasRepository;
import com.c2.sisteminformasitugas.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatkulServiceImpTest {
    @Mock
    private MatkulRepository matkulRepository;

    @InjectMocks
    private MatkulServiceImp matkulService;

    private Matkul matkul;

    @Mock
    private TugasServiceImpl tugasService;

    @Mock
    private TugasRepository tugasRepository;

    private Tugas tugas;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {

        user = new User();
        user.setNpm("1234");
        user.setEmail("test@gmail.com");
        user.setPassword("test123");
        user.setMatkulList(new ArrayList<>());

        matkul = new Matkul();
        matkul.setKodeMatkul("ADVPROG");
        matkul.setNama("Advanced Programming");
        matkul.setSubscribers(new ArrayList<>());

        tugas = new Tugas();
        tugas.setId(1);
        tugas.setJudul("Dummy");
        tugas.setDeadline(new Timestamp(System.currentTimeMillis()));
        tugas.setDeskripsi("Dummy");
        tugas.setLink("Dummy");
        tugas.setMatkul(matkul);

        userRepository.save(user);
        matkulRepository.save(matkul);
        tugasRepository.save(tugas);
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
    void testServiceUpdateMatkul() throws IOException, InterruptedException {
        matkulService.createMatkul(matkul);
        lenient().when(matkulService.getMatkul(matkul.getKodeMatkul())).thenReturn(matkul);
        matkul.setNama("Matkul Baru");
        Matkul expectedMatkul = matkul;
        expectedMatkul.setNama("Matkul Baru");
        Matkul result = matkulService.updateMatkul(matkul.getKodeMatkul(), matkul);
        Assertions.assertEquals(expectedMatkul.getNama(), result.getNama());
    }

    @Test
    void testServiceDeleteMatkul() {
        matkulService.createMatkul(matkul);
        matkulService.deleteMatkul(matkul.getKodeMatkul());
        Assertions.assertNull(matkulService.getMatkul(matkul.getKodeMatkul()));
    }

    @Test
    void testSubscribeToMatkul() {
        ListKodeMatkulDTO matkulDTO = new ListKodeMatkulDTO();
        String[] kodeMatkuls = {matkul.getKodeMatkul()};
        matkulDTO.setKodeMatkuls(Arrays.asList(kodeMatkuls));
        lenient().when(matkulRepository.findByKodeMatkul(matkul.getKodeMatkul())).thenReturn(matkul);
        User resultUser = matkulService.subscribeToMatkul(user, matkulDTO);
        Assertions.assertEquals(resultUser.getMatkulList().get(0).getNama(), matkul.getNama());
    }

    @Test
    void testUnsubscribeToMatkul() {
        ListKodeMatkulDTO matkulDTO = new ListKodeMatkulDTO();
        String[] kodeMatkuls = {matkul.getKodeMatkul()};
        matkulDTO.setKodeMatkuls(Arrays.asList(kodeMatkuls));
        lenient().when(matkulRepository.findByKodeMatkul(matkul.getKodeMatkul())).thenReturn(matkul);
        User subscribedUser = matkulService.subscribeToMatkul(user, matkulDTO);
        User unsubscribedUser = matkulService.unsubscribeToMatkul(subscribedUser, matkulDTO);
        Assertions.assertEquals(0, unsubscribedUser.getMatkulList().size());
    }
}
