package com.c2.sisteminformasitugas.controller;

import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.UserRepository;
import com.c2.sisteminformasitugas.security.constant.SecurityConstant;
import com.c2.sisteminformasitugas.security.provider.CustomAuthenticationProvider;
import com.c2.sisteminformasitugas.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserServiceImpl userService;

    @MockBean(answer = Answers.CALLS_REAL_METHODS)
    private CustomAuthenticationProvider authProvider;

    private User user;
    private String userJson;

    @BeforeEach
    public void setUp() throws Exception {
        user = new User("1906350616", "ganiilhamirsyadi@gmail.com", "password");
        userJson = "{\n" +
                "    \"npm\": \"1906350616\",\n" +
                "    \"email\": \"ganiilhamirsyadi@gmail.com\",\n" +
                "    \"password\": \"password\"\n" +
                "}";
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    private String getJWTToken() {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(SecurityConstant.HKEY)
                .compact();
    }

    @Test
    public void testControllerSignUpUser() throws Exception {
        when(userService.createUser(user)).thenReturn(user);
        // Sign Up a User
        mvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.npm").value("1906350616"));
    }

    @Test
    public void testControllerSignUpUserWithRegisteredEmail() throws Exception {
        when(userService.createUser(user)).thenThrow(new Exception());
        // Sign Up a User
        mvc.perform(post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("User with current email has been registered, please login"));
    }

    @Test
    public void testControllerGetUser() throws Exception {
        when(userService.convertTokenToUser(ArgumentMatchers.any())).thenReturn(user);
        mvc.perform(get("/user/")
                .header("Authorization", "Bearer " + getJWTToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.npm").value("1906350616"));
    }
}
