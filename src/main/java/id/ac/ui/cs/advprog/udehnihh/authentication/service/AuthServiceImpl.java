package id.ac.ui.cs.advprog.udehnihh.authentication.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.dto.AuthResponse;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.LoginRequest;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.RegisterRequest;
import id.ac.ui.cs.advprog.udehnihh.authentication.exception.UserNotFoundException;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final TokenBlacklistServiceImpl tokenBlacklistService;

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
            .email(request.getEmail())
            .fullName(request.getFullName())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail(), user.getRole().getValue(), user.getFullName());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.getEmail(), user.getRole().getValue(), user.getFullName());
        return new AuthResponse(token);
    }

    @Override
    public void logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid token format");
        }

        String token = authHeader.substring(7);

        if (!jwtService.validateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        tokenBlacklistService.blacklistToken(token);
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }
        throw new UserNotFoundException("User not found in SecurityContext");
    }
}
