package id.ac.ui.cs.advprog.udehnihh.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.AuthResponse;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.LoginRequest;
import id.ac.ui.cs.advprog.udehnihh.authentication.dto.RegisterRequest;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterSuccess() throws Exception {
        RegisterRequest request = new RegisterRequest("john@example.com", "John Doe", "Password.123");
        AuthResponse response = new AuthResponse("mocked-jwt-token");

        Mockito.when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
    }

    @Test
    void testRegisterValidationEmailIsEmpty() throws Exception {
        RegisterRequest request = new RegisterRequest("", "John Doe", "Password.123");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors[0]").value("Email is required"));
    }

    @Test
    void testRegisterValidationNameIsEmpty() throws Exception {
        RegisterRequest request = new RegisterRequest("john@example.com", "", "Password.123");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors[0]").value("Full name is required"));
    }

    @Test
    void testLoginSuccess() throws Exception {
        LoginRequest request = new LoginRequest("john@example.com", "Password.123");
        AuthResponse response = new AuthResponse("mocked-jwt-token");

        Mockito.when(authService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("mocked-jwt-token"));
    }

    @Test
    void testLoginFailure() throws Exception {
        LoginRequest request = new LoginRequest("john@example.com", "wrongpassword");

        Mockito.when(authService.login(any(LoginRequest.class)))
            .thenThrow(new RuntimeException("Invalid email or password"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Invalid email or password"));
    }

     @Test
    void testLogoutSuccess() throws Exception {
        doNothing().when(authService).logout("Bearer mocked-jwt-token");

        mockMvc.perform(post("/api/auth/logout")
                .header("Authorization", "Bearer mocked-jwt-token"))
            .andExpect(status().isOk())
            .andExpect(content().string("Logout successful"));
    }

    @Test
    void testLogoutFailure() throws Exception {
        doThrow(new IllegalArgumentException("Invalid or expired token."))
                .when(authService).logout("Bearer invalid-token");

        mockMvc.perform(post("/api/auth/logout")
                .header("Authorization", "Bearer invalid-token"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Invalid or expired token."));
    }
}
