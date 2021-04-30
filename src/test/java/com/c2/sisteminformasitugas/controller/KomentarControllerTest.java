package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.DTO.ListKomentarDTO;
import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.service.KomentarServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = KomentarController.class)
class KomentarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private KomentarServiceImpl komentarService;

    private Komentar komentar;

    @BeforeEach
    public void setUp(){
        komentar = new Komentar();
        komentar.setId(1);
        komentar.setComment("dummy");
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }


    @Test
    void testGetListKomentar() throws Exception {
        Iterable<Komentar> listKomentar = Arrays.asList(komentar);
        when(komentarService.getListKomentar()).thenReturn(listKomentar);

        mvc.perform(get("/komentar").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].id").value(komentar.getId()));


    }

    @Test
    void getKomentar() throws Exception {
        when(komentarService.getKomentar(komentar.getId())).thenReturn(komentar);
        mvc.perform(get("/komentar/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.comment").value("dummy"));

    }

    @Test
    void testCreateKomentar() throws Exception {
        when(komentarService.createKomentar(komentar)).thenReturn(komentar);

        mvc.perform(post("/komentar")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(komentar)))
                .andExpect(jsonPath("$.data.id").value(komentar.getId()));

    }

    @Test
    void deleteKomentar() throws Exception {
        komentarService.createKomentar(komentar);
        when(komentarService.deleteKomentar(komentar.getId())).thenReturn(1);

        mvc.perform(delete("/komentar/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Object deleted"));

    }
}