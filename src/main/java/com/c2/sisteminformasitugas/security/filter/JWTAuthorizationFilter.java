package com.c2.sisteminformasitugas.security.filter;

import com.c2.sisteminformasitugas.security.constant.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(SecurityConstant.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            SecurityContextHolder.clearContext();
            chain.doFilter(req, res);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstant.HEADER_STRING).replace(SecurityConstant.TOKEN_PREFIX,"");
        if (!token.equals("")) {
            // parse the token.
            var claims = Jwts.parserBuilder().setSigningKey(SecurityConstant.HKEY).build().parseClaimsJws(token).getBody();
            if (claims.getSubject() != null) {
                return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, new ArrayList<>());
            }
        }
        return null;
    }
}
