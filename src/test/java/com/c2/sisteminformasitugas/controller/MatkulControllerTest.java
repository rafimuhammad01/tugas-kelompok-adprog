package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.model.dto.ListKodeMatkulDTO;
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
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MatkulControllerTest {

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

        matkul = new Matkul();
        matkul.setKodeMatkul("123");
        matkul.setNama("Dummy");

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
    void testControllerCreateMatkul() throws Exception {
        when(matkulService.createMatkul(matkul)).thenReturn(matkul);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);

        mvc.perform(post("/matkul")
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(matkul)))
                .andExpect(jsonPath("$.kodeMatkul").value(matkul.getKodeMatkul()));
    }


    @Test
    void testControllerGetMatkul() throws Exception {
        when(matkulService.getMatkul(matkul.getKodeMatkul())).thenReturn(matkul);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);

        mvc.perform(get("/matkul/" + matkul.getKodeMatkul())
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nama").value(matkul.getNama()));
    }

    @Test
    void testControllerGetNonExistMatkul() throws Exception{
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);

        mvc.perform(get("/matkul/BASDAT")
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testControllerGetListMatkul() throws Exception {
        Iterable<Matkul> listMatkul = Arrays.asList(matkul);
        when(matkulService.getMatkul(matkul.getKodeMatkul())).thenReturn(matkul);
        when(matkulService.getListMatkul()).thenReturn(listMatkul);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);

        mvc.perform(get("/matkul")
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testControllerUpdateMatkul() throws Exception {
        matkulService.createMatkul(matkul);

        String namaMatkul = "ADV125YIHA";
        matkul.setNama(namaMatkul);

        when(matkulService.updateMatkul(matkul.getKodeMatkul(), matkul)).thenReturn(matkul);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);

        mvc.perform(put("/matkul/" + matkul.getKodeMatkul())
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(matkul)))
                .andExpect(status().isOk()).andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nama").value(namaMatkul));
    }

    @Test
    void testControllerSubscribeToMatkul() throws Exception {
        matkulService.createMatkul(matkul);

        String namaMatkul = "ADV125YIHA";
        matkul.setNama(namaMatkul);

        ListKodeMatkulDTO listKode = new ListKodeMatkulDTO();
        listKode.setKodeMatkuls(Collections.singletonList(matkul.getKodeMatkul()));
        user.setMatkulList(Collections.singletonList(matkul));
        when(matkulService.subscribeToMatkul(user,listKode)).thenReturn(user);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);

        mvc.perform(post("/matkul/subscribe")
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapToJson(listKode)))
                .andExpect(status().isOk());
    }

    @Test
    void testControllerDeleteMatkul() throws Exception {
        matkulService.createMatkul(matkul);
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);

        mvc.perform(delete("/matkul/" + matkul.getKodeMatkul())
                .header("Authorization", "Bearer " +  getJWTToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
