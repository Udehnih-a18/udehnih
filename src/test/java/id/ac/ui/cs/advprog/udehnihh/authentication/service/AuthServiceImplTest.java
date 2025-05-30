package id.ac.ui.cs.advprog.udehnihh.authentication.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.dto.AuthResponse;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.LoginRequest;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.RegisterRequest;
import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthServiceImplTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private TokenBlacklistServiceImpl tokenBlacklistService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtServiceImpl.class);
        tokenBlacklistService = mock(TokenBlacklistServiceImpl.class);
        authService = new AuthServiceImpl(userRepository, passwordEncoder, jwtService, tokenBlacklistService);
    }
    @Test
    void testGetCurrentUserReturnsUserFromContext() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .email("current@example.com")
                .fullName("Current User")
                .build();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        User currentUser = authService.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals("current@example.com", currentUser.getEmail());
    }

    @Test
    void testGetCurrentUserThrowsIfNotUserInstance() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("notAUser");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);

        assertThrows(RuntimeException.class, () -> authService.getCurrentUser());
    }

    @Test
    void testRegisterReturnsAuthResponseWithToken() {
        RegisterRequest request = new RegisterRequest("test@example.com", "Test User", "password");
        String encodedPassword = "encodedPassword";
        String token = "mockedToken";

        when(passwordEncoder.encode("password")).thenReturn(encodedPassword);
        when(jwtService.generateToken("test@example.com", "STUDENT", "Test User")).thenReturn(token);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        AuthResponse response = authService.register(request);

        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(encodedPassword, savedUser.getPassword());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("Test User", savedUser.getFullName());

        assertNotNull(response);
        assertEquals(token, response.getToken());
    }

    @Test
    void testLoginReturnsAuthResponseWithToken() {
        LoginRequest request = new LoginRequest("test@example.com", "password");
        User user = User.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .fullName("Test User")
                .role(Role.STUDENT)
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken("test@example.com", "STUDENT", "Test User")).thenReturn("mockedToken");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("mockedToken", response.getToken());
    }

    @Test
    void testLoginThrowsIfPasswordInvalid() {
        LoginRequest request = new LoginRequest("test@example.com", "wrongPassword");
        User user = User.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    void testLogoutStripsBearerAndBlacklistsToken() {
        String tokenWithBearer = "Bearer test.token.value";
        String token = "test.token.value";

        when(jwtService.validateToken(token)).thenReturn(true);

        authService.logout(tokenWithBearer);

        verify(tokenBlacklistService).blacklistToken(token);
    }

    @Test
    void testGetUserByIdThrowsIfNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.getUserById(id));
    }

    @Test
    void testGetUserByEmailThrowsIfNotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.getUserByEmail("nonexistent@example.com"));
    }
}
