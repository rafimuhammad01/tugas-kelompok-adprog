package com.c2.sisteminformasitugas.security.constant;
import java.security.Key;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class SecurityConstant {
    public static final Key HKEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static final long EXPIRATION_TIME = 9000_000; // 150 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

}
