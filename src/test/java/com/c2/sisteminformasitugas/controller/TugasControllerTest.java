package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.security.constant.SecurityConstant;
import com.c2.sisteminformasitugas.service.MatkulServiceImp;
import com.c2.sisteminformasitugas.service.TugasServiceImpl;
import com.c2.sisteminformasitugas.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TugasControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MatkulServiceImp matkulService;

    @MockBean
    private TugasServiceImpl tugasService;

    @MockBean
    private UserServiceImpl userService;

    private Tugas tugas;
    private Matkul matkul;
    private User user;
    private String userJson;

    @BeforeEach
    public void setUp() {

        tugas = new Tugas();
        tugas.setId(123);
        tugas.setJudul("Dummy");

        matkul = new Matkul();
        matkul.setKodeMatkul("12");
        matkul.setNama("Dummy2");

        user = new User();
        user.setNpm("1234");
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
    void testControllerCreateTugas() throws Exception {
        when(tugasService.createTugas(tugas)).thenReturn(tugas);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);

        mvc.perform(post("/tugas/" + matkul.getKodeMatkul())
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(tugas)))
                .andExpect(jsonPath("$.id").value(tugas.getId()));
    }


    @Test
    void testControllerGetTugas() throws Exception {
        when(tugasService.getTugas(tugas.getId())).thenReturn(tugas);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);

        mvc.perform(get("/tugas/" + tugas.getId())
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.judul").value(tugas.getJudul()));
    }

    @Test
    public void testControllerGetNonExistTugas() throws Exception{
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);

        mvc.perform(get("/tugas/69")
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testControllerGetListTugas() throws Exception {
        Iterable<Tugas> listMatkul = Arrays.asList(tugas);
        when(tugasService.getTugas(tugas.getId())).thenReturn(tugas);
        when(tugasService.getListTugas(matkul.getKodeMatkul())).thenReturn(listMatkul);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);
        tugas.setMatkul(matkul);
        mvc.perform(get("/tugas/matkul/" + matkul.getKodeMatkul())
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    void testControllerUpdateMatkul() throws Exception {
//        matkulService.createMatkul(matkul);
//
//        String namaMatkul = "ADV125YIHA";
//        matkul.setNama(namaMatkul);
//
//        when(matkulService.updateMatkul(matkul.getKodeMatkul(), matkul)).thenReturn(matkul);
//        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);
//
//        mvc.perform(put("/matkul/" + matkul.getKodeMatkul())
//                .header("Authorization", "Bearer " +  getJWTToken())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapToJson(matkul)))
//                .andExpect(status().isOk()).andExpect(content()
//                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.nama").value(namaMatkul));
//    }
//
//    @Test
//    void testControllerDeleteMatkul() throws Exception {
//        matkulService.createMatkul(matkul);
//        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);
//
//        mvc.perform(delete("/matkul/" + matkul.getKodeMatkul())
//                .header("Authorization", "Bearer " +  getJWTToken())
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }

}