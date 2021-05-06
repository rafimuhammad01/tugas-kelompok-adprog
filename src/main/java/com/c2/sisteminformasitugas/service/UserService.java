package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    User createUser(User user) throws Exception;

    User getUserByEmail(String email);

    User convertTokenToUser(HttpServletRequest request);
}
