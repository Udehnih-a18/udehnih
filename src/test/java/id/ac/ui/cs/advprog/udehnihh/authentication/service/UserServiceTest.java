package id.ac.ui.cs.advprog.udehnihh.authentication.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);

        user = User.builder()
                .email("user@example.com")
                .fullName("User Example")
                .password("password123")
                .build();
    }

    @Test
    void testRegisterUserWithEmptyEmail() {
        user.setEmail("");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });
        assertEquals("Email tidak boleh kosong", exception.getMessage());
    }

    @Test
    void testRegisterUserWithEmptyPassword() {
        user.setPassword("");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });
        assertEquals("Password tidak boleh kosong", exception.getMessage());
    }

    @Test
    void testRegisterUserWithExistingEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user);
        });
        assertEquals("Email sudah terdaftar", exception.getMessage());
    }

    @Test
    void testAuthenticateSuccess() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User result = userService.authenticate("user@example.com", "password123");

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testAuthenticateWithWrongPassword() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticate("user@example.com", "wrongpassword");
        });
        assertEquals("Email atau password salah", exception.getMessage());
    }

    @Test
    void testAuthenticateWithUnregisteredEmail() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.authenticate("notfound@example.com", "password123");
        });
        assertEquals("Email atau password salah", exception.getMessage());
    }
}
