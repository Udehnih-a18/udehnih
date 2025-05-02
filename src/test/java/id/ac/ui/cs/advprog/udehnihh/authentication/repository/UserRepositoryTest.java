package id.ac.ui.cs.advprog.udehnihh.authentication.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
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
