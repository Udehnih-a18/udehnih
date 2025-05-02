package id.ac.ui.cs.advprog.udehnihh.authentication.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

public class UserRepositoryTest {
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("user@example.com");
        user.setFullName("User Example");
        user.setPassword("password123");
        userRepository.save(user);
    }

    @Test
    void testFindByEmail() {
        User foundUser = userRepository.findByEmail(user.getEmail());

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }
}
