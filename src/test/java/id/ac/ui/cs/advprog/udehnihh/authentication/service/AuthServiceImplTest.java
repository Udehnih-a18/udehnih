package id.ac.ui.cs.advprog.udehnihh.authentication.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.dto.AuthResponse;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.LoginRequest;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.RegisterRequest;
import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private TokenBlacklistServiceImpl tokenBlacklistService;

    @InjectMocks
    private AuthServiceImpl authService;

    private final String testEmail = "test@example.com";
    private final String testName = "Test User";
    private final String testPassword = "password";
    private final String encodedPassword = "encodedPassword";
    private final String testToken = "test.token.value";

    @Test
    void testRegister_Success() {
        // Arrange
        RegisterRequest request = new RegisterRequest(testEmail, testName, testPassword);
        User expectedUser = User.builder()
                .email(testEmail)
                .fullName(testName)
                .password(encodedPassword)
                .role(Role.STUDENT)
                .build();

        when(passwordEncoder.encode(testPassword)).thenReturn(encodedPassword);
        when(jwtService.generateToken(testEmail, Role.STUDENT.getValue())).thenReturn(testToken);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        AuthResponse response = authService.register(request);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals(testEmail, savedUser.getEmail());
        assertEquals(testName, savedUser.getFullName());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertEquals(Role.STUDENT, savedUser.getRole());
        assertEquals(testToken, response.getToken());
    }

    @Test
    void testLogin_Success() {
        // Arrange
        LoginRequest request = new LoginRequest(testEmail, testPassword);
        User user = User.builder()
                .email(testEmail)
                .password(encodedPassword)
                .role(Role.STUDENT)
                .build();

        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(testPassword, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(testEmail, Role.STUDENT.getValue())).thenReturn(testToken);

        // Act
        AuthResponse response = authService.login(request);

        // Assert
        assertEquals(testToken, response.getToken());
    }

    @Test
    void testLogin_InvalidEmail_ThrowsException() {
        // Arrange
        LoginRequest request = new LoginRequest("invalid@example.com", testPassword);
        when(userRepository.findByEmail("invalid@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    void testLogin_InvalidPassword_ThrowsException() {
        // Arrange
        LoginRequest request = new LoginRequest(testEmail, "wrongPassword");
        User user = User.builder()
                .email(testEmail)
                .password(encodedPassword)
                .build();

        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", encodedPassword)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    void testLogout_Success() {
        // Arrange
        String authHeader = "Bearer " + testToken;
        when(jwtService.validateToken(testToken)).thenReturn(true);

        // Act
        authService.logout(authHeader);

        // Assert
        verify(tokenBlacklistService).blacklistToken(testToken);
    }

    @Test
    void testLogout_InvalidTokenFormat_ThrowsException() {
        // Arrange
        String invalidHeader = "InvalidTokenFormat";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> authService.logout(invalidHeader));
        verify(tokenBlacklistService, never()).blacklistToken(any());
    }

    @Test
    void testLogout_InvalidToken_ThrowsException() {
        // Arrange
        String authHeader = "Bearer " + testToken;
        when(jwtService.validateToken(testToken)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> authService.logout(authHeader));
        verify(tokenBlacklistService, never()).blacklistToken(any());
    }

    @Test
    void testGetUserById_Success() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User expectedUser = User.builder()
                .id(userId)
                .email(testEmail)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = authService.getUserById(userId);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void testGetUserById_NotFound_ThrowsException() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.getUserById(userId));
    }

    @Test
    void testGetUserByEmail_Success() {
        // Arrange
        User expectedUser = User.builder()
                .email(testEmail)
                .build();

        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = authService.getUserByEmail(testEmail);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void testGetUserByEmail_NotFound_ThrowsException() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.getUserByEmail("nonexistent@example.com"));
    }
}