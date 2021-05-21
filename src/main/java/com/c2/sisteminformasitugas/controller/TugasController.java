package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.service.MatkulService;
import com.c2.sisteminformasitugas.service.TugasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/tugas")
public class TugasController {
    @Autowired
    private TugasService tugasService;

    @Autowired
    private MatkulService matkulService;

    @GetMapping(path = "matkul/{kodeMatkul}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Iterable<Tugas>> getListTugas(@PathVariable(value = "kodeMatkul") String kodeMatkul) {
        return ResponseEntity.ok(tugasService.getListTugas(kodeMatkul));
    }

    @PostMapping(path = "/{kodeMatkul}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity createTugas(@RequestBody Tugas tugas, @PathVariable(value = "kodeMatkul") String kodeMatkul) throws IOException, InterruptedException {
        tugas.setMatkul(matkulService.getMatkul(kodeMatkul));
        return ResponseEntity.ok(tugasService.createTugas(tugas));
    }

    @GetMapping(path = "/{kodeTugas}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getTugas(@PathVariable(value = "kodeTugas") int kodeTugas) {
        var tugas = tugasService.getTugas(kodeTugas);
        if (tugas == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(tugas);
    }

    @PutMapping(path = "/{kodeTugas}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity updateTugas(@PathVariable(value = "kodeTugas") int kodeTugas, @RequestBody Tugas tugas) {
        return ResponseEntity.ok(tugasService.updateTugas(kodeTugas, tugas));
    }

    @DeleteMapping(path = "/{kodeTugas}", produces = {"application/json"})
    public ResponseEntity deleteTugas(@PathVariable(value = "kodeTugas") int kodeTugas) {
        tugasService.deleteTugas(kodeTugas);
        return ResponseEntity.noContent().build();
    }
}
