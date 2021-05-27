package com.c2.sisteminformasitugas.security.filter;

import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.security.constant.SecurityConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/user/login", "POST"), authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(req);
        return authenticationManager.authenticate(authRequest);
    }

    public UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest req) throws Exception{
        User credentials = new ObjectMapper().readValue(req.getInputStream(), User.class);
        return new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req,
                                         HttpServletResponse res,
                                         FilterChain chain,
                                         Authentication auth) throws IOException {
        String token = Jwts.builder()
                .setSubject((String) auth.getPrincipal())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(SecurityConstant.HKEY)
                .compact();

        res.addHeader("Access-Control-Expose-Headers", "*");
        res.addHeader("Content-Type", "text/plain");
        res.getWriter().write(token);
        res.getWriter().flush();
    }
}
