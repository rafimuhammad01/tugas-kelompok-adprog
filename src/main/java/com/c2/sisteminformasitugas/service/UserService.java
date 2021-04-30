package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.User;

public interface UserService {
    User createUser(User user);

    User getUserByEmail(String email);
}
