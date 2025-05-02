package id.ac.ui.cs.advprog.udehnihh.authentication.model;

import enums.Gender;
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
        user.setGender(Gender.valueOf("FEMALE"));
        user.setRegistrationDate(registrationDate);
        user.setPhoneNumber("081234567890");
        user.setBirthDate("2003-05-22");
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
    void testGender() {
        assertEquals(Gender.FEMALE, user.getGender());
    }

    @Test
    void testRegistrationDate() {
        assertEquals(registrationDate, user.getRegistrationDate());
    }

    @Test
    void testPhoneNumber() {
        assertEquals("081234567890", user.getPhoneNumber());
    }

    @Test
    void testBirthDate() {
        assertEquals("2003-05-22", user.getBirthDate());
    }
}
