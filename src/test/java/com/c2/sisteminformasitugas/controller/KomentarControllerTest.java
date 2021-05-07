package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.DTO.ListKomentarDTO;
import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.security.constant.SecurityConstant;
import com.c2.sisteminformasitugas.service.*;
import com.c2.sisteminformasitugas.util.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = KomentarController.class)
class KomentarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private KomentarServiceImpl komentarService;

    @MockBean
    private TugasServiceImpl tugasService;

    @MockBean
    private UserServiceImpl userService;

    private Komentar komentar;
    private Tugas tugas;
    private Matkul matkul;
    private User user;
    private String userJson;
    private Tugas tugasPassedDeadline;

    @BeforeEach
    public void setUp() {
        komentar = new Komentar();
        komentar.setId(1);
        komentar.setComment("Dummy");

        matkul = new Matkul();
        matkul.setKodeMatkul("123");
        matkul.setNama("Dummy");

        tugas = new Tugas();
        tugas.setId(1);
        tugas.setJudul("Dummy");
        tugas.setDeadline(Timestamp.valueOf("2022-09-01 09:01:15"));
        tugas.setDeskripsi("Dummy");
        tugas.setLink("Dummy");
        tugas.setMatkul(matkul);

        tugasPassedDeadline = new Tugas();
        tugasPassedDeadline.setId(2);
        tugasPassedDeadline.setJudul("Dummy");
        tugasPassedDeadline.setDeadline(Timestamp.valueOf("2002-09-01 09:01:15"));
        tugasPassedDeadline.setDeskripsi("Dummy");
        tugasPassedDeadline.setLink("Dummy");
        tugasPassedDeadline.setMatkul(matkul);

        user = new User();
        user.setNpm("1234");
        user.setAdmin(false);
        user.setEmail("test@gmail.com");
        user.setPassword("test123");

        userJson = "{\n" +
                "    \"npm\": \"1906350616\",\n" +
                "    \"email\": \"ganiilhamirsyadi@gmail.com\",\n" +
                "    \"password\": \"password\"\n" +
                "}";
    }

    private String getJWTToken() {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(SecurityConstant.HKEY)
                .compact();
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void testGetListKomentar() throws Exception {
        Iterable<Komentar> listKomentar = Arrays.asList(komentar);
        when(tugasService.getTugas(tugas.getId())).thenReturn(tugas);
        when(komentarService.getListKomentar(tugas)).thenReturn(listKomentar);

        System.out.println(komentarService.getListKomentar(tugas));
        mvc.perform(get("/komentar/1").header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(komentar.getId()));
    }

    @Test
    void testCreateKomentar() throws Exception {
        when(komentarService.createKomentar(komentar, user, tugas)).thenReturn(komentar);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);
        when(tugasService.getTugas(tugas.getId())).thenReturn(tugas);

        mvc.perform(post("/komentar/1")
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(komentar)))
                .andExpect(jsonPath("$.data.id").value(komentar.getId()));

    }

    @Test
    void testCreateKomentarTugasNotFound() throws Exception {
        when(komentarService.createKomentar(komentar, user, tugas)).thenReturn(komentar);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);
        when(tugasService.getTugas(tugas.getId())).thenReturn(null);

        mvc.perform(post("/komentar/1")
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(komentar)))
                .andExpect(jsonPath("$.message").value("tugas not found"));

    }

    @Test
    void testCreateKomentarDeadlinePassed() throws Exception {
        when(komentarService.createKomentar(komentar, user, tugas)).thenReturn(null);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);
        when(tugasService.getTugas(tugas.getId())).thenReturn(tugas);

        mvc.perform(post("/komentar/1")
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(komentar)))
                .andExpect(jsonPath("$.message").value("task deadline has passed"));

    }

    @Test
    void deleteKomentar() throws Exception {
        when(komentarService.deleteKomentar(komentar.getId())).thenReturn(Helper.COMMENT_DELETED);

        mvc.perform(delete("/komentar/1")
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("comment deleted"));

    }

    @Test
    void deleteKomentarNotFound() throws Exception {
        when(komentarService.deleteKomentar(komentar.getId())).thenReturn(Helper.COMMENT_NOT_FOUND);

        mvc.perform(delete("/komentar/1")
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("comment not found"));

    }


}

