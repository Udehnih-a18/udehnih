package id.ac.ui.cs.advprog.udehnihh.authentication.model;

import enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private LocalDateTime registrationDate;

    @BeforeEach
    void setUp() {
        registrationDate = LocalDateTime.now();

        user = new User();
        user.setEmail("user@example.com");
        user.setFullName("User Example");
        user.setPassword("password123");
        user.setRole(Role.valueOf("STUDENT"));
        user.setRegistrationDate(registrationDate);
    }

    @Test
    void testEmail() {
        assertEquals("user@example.com", user.getEmail());
    }

    @Test
    void testFullName() {
        assertEquals("User Example", user.getFullName());
    }

    @Test
    void testPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testRole() {
        assertEquals(Role.STUDENT, user.getRole());
    }

    @Test
    void testRegistrationDate() {
        assertEquals(registrationDate, user.getRegistrationDate());
    }
}
