package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/signup", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<User> postMahasiswa(@RequestBody User user, HttpServletResponse response) {
        return ResponseEntity.ok(userService.createUser(user));
    }
}
