package id.ac.ui.cs.advprog.udehnihh.authentication.service;

import java.util.UUID;

import id.ac.ui.cs.advprog.udehnihh.authentication.dto.AuthResponse;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.LoginRequest;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.RegisterRequest;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    void logout(String token);
    User getUserById(UUID id);
    User getUserByEmail(String email);
    User getCurrentUser();
}