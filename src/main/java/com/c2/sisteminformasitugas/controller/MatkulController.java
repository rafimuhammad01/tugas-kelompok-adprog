package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.Matkul;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.service.MatkulService;
import com.c2.sisteminformasitugas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/matkul")
public class MatkulController {
    @Autowired
    private MatkulService matkulService;

    @Autowired
    private UserService userService;

    @GetMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Iterable<Matkul>> getListMataKuliah() {
        return ResponseEntity.ok(matkulService.getListMatkul());
    }

    @PostMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity createMatkul(@RequestBody Matkul matkul) {
        System.out.println("Matkul controller : \n" + matkul);
        return ResponseEntity.ok(matkulService.createMatkul(matkul));
    }

    @GetMapping(path = "/{kodeMatkul}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getMatkul(@PathVariable(value = "kodeMatkul") String kodeMatkul) {
        Matkul matkul = matkulService.getMatkul(kodeMatkul);
        if (matkul == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(matkul);
    }

    @PutMapping(path = "/{kodeMatkul}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity updateMatkul(@PathVariable(value = "kodeMatkul") String kodeMatkul, @RequestBody Matkul matkul) {
        return ResponseEntity.ok(matkulService.updateMatkul(kodeMatkul, matkul));
    }

    @DeleteMapping(path = "/{kodeMatkul}", produces = {"application/json"})
    public ResponseEntity deleteMatkul(@PathVariable(value = "kodeMatkul") String kodeMatkul) {
        matkulService.deleteMatkul(kodeMatkul);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

//    @PostMapping(path = "/subscribeMatkuls")
//    @ResponseBody
//    public ResponseEntity subscribeMatkuls(@RequestBody String[] matkuls, HttpServletRequest request) {
//        User user = userService.convertTokenToUser(request);
//        for (String kodeMatkul:matkuls) {
//            matkulService.addSubscriber(kodeMatkul, user);
//        }
//        return ResponseEntity.ok().body("SUCCESS");
//    }
}