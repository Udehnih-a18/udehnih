package id.ac.ui.cs.advprog.udehnihh.authentication.model;

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
        user.setEmail("madeline@example.com");
        user.setFullName("Madeline Clairine Gultom");
        user.setPassword("securepassword");
        user.setRole("STUDENT");
        user.setGender("FEMALE");
        user.setRegistrationDate(registrationDate);
        user.setPhoneNumber("081234567890");
        user.setBirthDate("2003-05-22");
    }

    @Test
    void testEmail() {
        assertEquals("madeline@example.com", user.getEmail());
    }

    @Test
    void testFullName() {
        assertEquals("Madeline Clairine Gultom", user.getFullName());
    }

    @Test
    void testPassword() {
        assertEquals("securepassword", user.getPassword());
    }

    @Test
    void testRole() {
        assertEquals("STUDENT", user.getRole());
    }

    @Test
    void testGender() {
        assertEquals("FEMALE", user.getGender());
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
