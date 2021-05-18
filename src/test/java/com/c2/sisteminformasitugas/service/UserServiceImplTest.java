package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.MatkulRepository;
import com.c2.sisteminformasitugas.repository.UserRepository;
import com.c2.sisteminformasitugas.security.constant.SecurityConstant;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
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

    @Test
    public void testConvertTokenToUser() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String token = Jwts.builder()
                .setSubject("ganiilhamirsyadi@gmail.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(SecurityConstant.HKEY)
                .compact();
        request.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX + token);
        lenient().when(userService.convertTokenToUser(request)).thenReturn(user);
        User userFound = userService.convertTokenToUser(request);
        Assertions.assertEquals(userFound.getEmail(), user.getEmail());
    }

    @Test
    public void testConvertTokenToUserNotFound() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX);
        User userFound = userService.convertTokenToUser(request);
        Assertions.assertNull(userFound);
    }
}
