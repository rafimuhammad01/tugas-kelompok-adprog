package com.c2.sisteminformasitugas.Controller;

import com.c2.sisteminformasitugas.controller.MatkulController;
import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.service.MatkulServiceImp;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = MatkulController.class)
public class MatkulControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MatkulServiceImp matkulService;

    private Matkul matkul;

//    @BeforeEach
//    public void setUp() {
////        matkul = new Matkul("ADVPROG", "Advanced Programming");
//    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void testControllerGetListMatkul() throws Exception {
        Iterable<Matkul> listMatkul = Arrays.asList(matkul);
        when(matkulService.getListMatkul()).thenReturn(listMatkul);

        mvc.perform(get("/matkul").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].kodeMatkul").value(matkul.getKodeMatkul()));
    }

//    @Test
//    void testControllerCreateMatkul() throws Exception {
//        when(matkulService.createMatkul(matkul)).thenReturn(matkul);
//
//        mvc.perform(post("/matkul")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(mapToJson(matkul)))
//                .andExpect(jsonPath("$.kodeMatkul").value(matkul.getKodeMatkul()));
//    }
//
//    @Test
//    void testControllerGetMatkul() throws Exception {
//        when(matkulService.getMatkul(matkul.getKodeMatkul())).thenReturn(matkul);
//        mvc.perform(get("/matkul/" + matkul.getKodeMatkul()).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()).andExpect(content()
//                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.nama").value(matkul.getNama()));
//    }
//
//    @Test
//    public void testControllerGetNonExistMatkul() throws Exception{
//        mvc.perform(get("/matkul/BASDAT").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }

//    @Test
//    void testControllerUpdateMatkul() throws Exception {
//        matkulService.createMatkul(matkul);
//
//        String namaMatkul = "ADV125YIHA";
//        matkul.setNama(namaMatkul);
//
//        when(matkulService.updateMatkul(matkul.getKodeMatkul(), matkul)).thenReturn(matkul);
//
//        mvc.perform(put("/matkul/" + matkul.getKodeMatkul()).contentType(MediaType.APPLICATION_JSON)
//                .content(mapToJson(matkul)))
//                .andExpect(status().isOk()).andExpect(content()
//                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.nama").value(namaMatkul));
//    }
//
//    @Test
//    void testControllerDeleteMatkul() throws Exception {
//        matkulService.createMatkul(matkul);
//        mvc.perform(delete("/matkul/" + matkul.getKodeMatkul()).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }

}
