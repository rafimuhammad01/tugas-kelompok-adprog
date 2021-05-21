package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.error.ApiError;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/signup", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity signUp(@RequestBody User user, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiError(
                            HttpStatus.BAD_REQUEST,
                            "User with current email has been registered, please login",
                            e));
        }
    }

    @GetMapping(path = "/", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<User> getUser(HttpServletRequest request, HttpServletResponse response) {
        var user = userService.convertTokenToUser(request);
        return ResponseEntity.ok(user);
    }
}
