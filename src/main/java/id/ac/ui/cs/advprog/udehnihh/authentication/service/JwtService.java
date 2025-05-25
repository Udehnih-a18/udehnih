package id.ac.ui.cs.advprog.udehnihh.authentication.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface JwtService {

    String generateToken(String email, String role, String fullName);
    String getEmailFromToken(String token);
    String getRoleFromToken(String token);
    String getFullNameFromToken(String token);
    boolean validateToken(String token);
    Jws<Claims> parseToken(String token);
}
