package com.test.RegisterLogin.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtils {

    // Generate a secure key for HS256
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(int employeeId, String employeeName) {
        return Jwts.builder()
                .setSubject("Employee Details")
                .claim("employee_id", employeeId)
                .claim("employee_name", employeeName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours validity
                .signWith(SECRET_KEY) // Use the secure key
                .compact();
    }
}
