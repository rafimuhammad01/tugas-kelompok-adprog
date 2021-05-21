package com.c2.sisteminformasitugas.service;

import com.c2.sisteminformasitugas.model.User;
import com.c2.sisteminformasitugas.repository.UserRepository;
import com.c2.sisteminformasitugas.security.constant.SecurityConstant;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override

    public User createUser(User user) throws Exception {
        var userFound = userRepository.findByEmail(user.getEmail());
        if (userFound != null) throw new Exception();
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User convertTokenToUser(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstant.HEADER_STRING).replace(SecurityConstant.TOKEN_PREFIX,"");
        if (!token.equals("")) {
            // parse the token.
            var claims = Jwts.parserBuilder().setSigningKey(SecurityConstant.HKEY).build().parseClaimsJws(token).getBody();
            if (claims.getSubject() != null) {
                return getUserByEmail(claims.getSubject());
            }
        }
        return null;
    }
}
