package id.ac.ui.cs.advprog.udehnihh.authentication.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

public interface UserService {
    User registerUser(User user);
    User authenticate(String email, String password);
    User getUserById(Long id);
    User getUserByEmail(String email);
}
