package id.ac.ui.cs.advprog.udehnihh.authentication.service;

import java.util.UUID;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

public interface UserService {
    User registerUser(User user);
    User authenticate(String email, String password);
    User getUserById(UUID id);
    User getUserByEmail(String email);
}
