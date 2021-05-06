package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override

    public User createUser(User user) throws Exception {
        User userFound = userRepository.findByEmail(user.getEmail());
        if (userFound != null) throw new Exception();
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        user.setAdmin(false);
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
