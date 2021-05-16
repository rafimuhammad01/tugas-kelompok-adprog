package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private MatkulRepository matkulRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("1906350616", "ganiilhamirsyadi@gmail.com", "password", false);
    }

    @Test
    public void testServiceCreateUser() throws Exception {
        lenient().when(userService.createUser(user)).thenReturn(user);
    }

    @Test
    public void testServiceGetUserByEmail() throws Exception {
        lenient().when(userService.getUserByEmail("ganiilhamirsyadi@gmail.com")).thenReturn(user);
        User userFound = userService.getUserByEmail(user.getEmail());
        Assertions.assertEquals(userFound.getEmail(), user.getEmail());
    }
}
