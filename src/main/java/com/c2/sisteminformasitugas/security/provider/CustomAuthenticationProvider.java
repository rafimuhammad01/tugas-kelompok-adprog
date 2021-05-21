package com.c2.sisteminformasitugas.security.provider;

import com.c2.sisteminformasitugas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String email = (String) auth.getPrincipal();
        String password = (String) auth.getCredentials();

        var user = userService.getUserByEmail(email);

        if (user == null) throw new BadCredentialsException("User not found");


        boolean passwordMatch = bCryptPasswordEncoder().matches(password, user.getPassword());

        if (!passwordMatch) throw new BadCredentialsException("Password did not match");

        return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
