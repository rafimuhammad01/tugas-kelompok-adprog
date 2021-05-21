package com.c2.sisteminformasitugas.security;

import com.c2.sisteminformasitugas.controller.UserController;
import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.UserRepository;
import com.c2.sisteminformasitugas.security.filter.JWTAuthenticationFilter;
import com.c2.sisteminformasitugas.security.provider.CustomAuthenticationProvider;
import com.c2.sisteminformasitugas.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private CustomAuthenticationProvider authProvider;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    private User user;
    private UsernamePasswordAuthenticationToken userToken;

    @BeforeEach
    public void setup() throws Exception {
        user = new User("1906350616", "ganiilhamirsyadi@gmail.com", "password");
        userToken = new UsernamePasswordAuthenticationToken(user.getEmail(), "password");
    }

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @WithMockUser(value = "ganiilhamirsyadi@gmail.com", password = "password")
    @Test
    public void testLoginUnauthorized() throws Exception {
        String jsonBody = "{\n" +
                "    \"email\": \"ganiilhamirsyadi@gmail.com\",\n" +
                "    \"password\": \"password2\"\n" +
                "}";
        when(authProvider.supports(any())).thenReturn(true);
        when(authProvider.bCryptPasswordEncoder()).thenReturn(bCryptPasswordEncoder());
        when(userService.createUser(any())).thenReturn(user);
        when(authProvider.authenticate(userToken)).thenReturn(userToken);
        MvcResult mvcResult = mvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void testAttemptAuthentication() throws Exception {
        when(jwtAuthenticationFilter.attemptAuthentication(any(), any())).thenReturn((userToken));
        lenient().when(jwtAuthenticationFilter.getAuthRequest(any())).thenReturn(userToken);
        Authentication result = jwtAuthenticationFilter.attemptAuthentication(any(), any());
        Assertions.assertEquals(userToken, result);
    }

}
