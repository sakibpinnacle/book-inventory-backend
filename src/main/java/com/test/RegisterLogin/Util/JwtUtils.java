package com.test.RegisterLogin.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtils {

    // Use a custom secret key (like in the second code)
//    private static final String SECRET_KEY = "sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh"; // You should replace this with a more secure way of managing the key
    private static final String SECRET_KEY = "sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh sakibsheikh"; // You should replace this with a more secure way of managing the key

    // Method to generate JWT token using employee details (with expiration and custom secret key)
    public static String generateToken(int employeeId, String employeeName) {
        return Jwts.builder()
                .setSubject("Employee Details") // Subject of the token, can be 'Employee Details'
                .claim("employee_id", employeeId) // Store employee ID as claim
                .claim("employee_name", employeeName) // Store employee name as claim
                .setIssuedAt(new Date()) // Set the issued time of the token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20)) //20 minuts validity
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Use custom secret key with HMAC256
                .compact(); // Return the generated token
    }

    // Method to validate JWT token (similar to the second code)
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Use custom secret key for validation
                    .build()
                    .parseClaimsJws(token); // Parse the token to validate it
            return true;
        } catch (Exception e) {
            return false; // Return false if any exception occurs (e.g., invalid signature or expired token)
        }
    }

    // Method to extract employee ID from the token (since this token is based on employee details)
    public static Integer extractEmployeeId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Use custom secret key for validation
                .build()
                .parseClaimsJws(token) // Parse the token
                .getBody()
                .get("employee_id", Integer.class); // Extract employee ID from the claims
    }

    // Method to extract employee name from the token (similar to extracting employee ID)
    public static String extractEmployeeName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())) // Use custom secret key for validation
                .build()
                .parseClaimsJws(token) // Parse the token
                .getBody()
                .get("employee_name", String.class); // Extract employee name from the claims
    }
}
