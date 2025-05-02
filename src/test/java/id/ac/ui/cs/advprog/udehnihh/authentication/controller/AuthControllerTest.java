package id.ac.ui.cs.advprog.udehnihh.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User dummyUser;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        dummyUser = new User();
        dummyUser.setId(UUID.randomUUID());
        dummyUser.setEmail("test@example.com");
        dummyUser.setPassword("password123");

        objectMapper = new ObjectMapper();
    }

    @Test
    public void testRegisterUserSuccess() throws Exception {
        Mockito.when(userService.registerUser(any(User.class))).thenReturn(dummyUser);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dummyUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(dummyUser.getEmail()));
    }

    @Test
    public void testRegisterUserBadRequest() throws Exception {
        Mockito.when(userService.registerUser(any(User.class)))
               .thenThrow(new IllegalArgumentException("Email tidak boleh kosong"));

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dummyUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginSuccess() throws Exception {
        Mockito.when(userService.authenticate(dummyUser.getEmail(), dummyUser.getPassword()))
            .thenReturn(dummyUser);

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", dummyUser.getEmail());
        loginRequest.put("password", dummyUser.getPassword());

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(dummyUser.getEmail()));
    }


    @Test
    public void testLoginUnauthorized() throws Exception {
        Mockito.when(userService.authenticate(anyString(), anyString()))
            .thenThrow(new IllegalArgumentException("Email atau password salah"));

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", dummyUser.getEmail());
        loginRequest.put("password", dummyUser.getPassword());

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

}
