package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.DTO.ListKomentarDTO;
import com.c2.sisteminformasitugas.model.DTO.NoContentDTO;
import com.c2.sisteminformasitugas.model.DTO.SingleKomentarDTO;
import com.c2.sisteminformasitugas.model.Komentar;
import com.c2.sisteminformasitugas.model.Tugas;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.service.KomentarService;
import com.c2.sisteminformasitugas.service.TugasService;
import com.c2.sisteminformasitugas.service.UserService;
import com.c2.sisteminformasitugas.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path= "/komentar")
public class KomentarController {
    @Autowired
    private KomentarService komentarService;

    @Autowired
    private UserService userService;

    @Autowired
    private TugasService tugasService;

    @GetMapping(path= "/{kodeTugas}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getListKomentar(@PathVariable(value = "kodeTugas") int kodeTugas) {
        // Get Tugas for search by tugas
        Tugas tugas = tugasService.getTugas(kodeTugas);

        // Response
        ListKomentarDTO response = new ListKomentarDTO();
        response.setData(komentarService.getListKomentar(tugas));
        response.setMessage("success");
        response.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @PostMapping(path= "/{kodeTugas}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity createKomentar(@RequestBody Komentar komentar, HttpServletRequest request, @PathVariable(value = "kodeTugas") int kodeTugas) {
        // Get User data for author field and tugas data in Komentar table
        User user = userService.convertTokenToUser(request);
        Tugas tugas = tugasService.getTugas(kodeTugas);

        // Response
        // Check if tugas exist
        if (tugas == null) {
            NoContentDTO failedResponse = new NoContentDTO();
            failedResponse.setMessage("tugas not found");
            failedResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(failedResponse, HttpStatus.NOT_FOUND);
        }
        // if tugas exist
        SingleKomentarDTO response = new SingleKomentarDTO();
        Komentar komentarResponse = komentarService.createKomentar(komentar, user, tugas);
        if (komentarResponse != null) {
            response.setData(komentarResponse);
            response.setMessage("comment created");
            response.setStatus(HttpStatus.CREATED.value());
        } else {
            NoContentDTO failedResponse = new NoContentDTO();
            failedResponse.setMessage("task deadline has passed");
            failedResponse.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(failedResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(path="/{id}",produces = {"application/json"})
    @ResponseBody
    public ResponseEntity deleteKomentar(@PathVariable(value = "id") int id){
        int res = komentarService.deleteKomentar(id);
        NoContentDTO response = new NoContentDTO();
        response.setStatus(HttpStatus.OK.value());
        if (res == Helper.COMMENT_DELETED) {
            response.setMessage("comment deleted");
        } else {
            response.setMessage("comment not found");
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
