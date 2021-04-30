package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.DTO.ListKomentarDTO;
import com.c2.sisteminformasitugas.model.DTO.NoContentDTO;
import com.c2.sisteminformasitugas.model.DTO.SingleKomentarDTO;
import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.service.KomentarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= "/komentar")
public class KomentarController {


    @Autowired
    private KomentarService komentarService;


    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getListKomentar() {
        ListKomentarDTO response = new ListKomentarDTO();
        response.setData(komentarService.getListKomentar());
        response.setMessage("success");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping(path= "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getKomentar(@PathVariable(value="id") int id) {
        SingleKomentarDTO response = new SingleKomentarDTO();
        response.setData(komentarService.getKomentar(id));
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("success");
        return ResponseEntity.ok(response);
    }

    @PostMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity createKomentar(@RequestBody Komentar komentar) {
        SingleKomentarDTO response = new SingleKomentarDTO();
        response.setData(komentarService.createKomentar(komentar));
        response.setMessage("created");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path="/{id}",produces = {"application/json"})
    @ResponseBody
    public ResponseEntity deleteKomentar(@PathVariable(value = "id") int id){
        int res = komentarService.deleteKomentar(id);
        NoContentDTO response = new NoContentDTO();
        response.setStatus(HttpStatus.OK.value());
        if (res == 1) {
            response.setMessage("Object deleted");
        } else {
            response.setMessage("Object not found");
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
