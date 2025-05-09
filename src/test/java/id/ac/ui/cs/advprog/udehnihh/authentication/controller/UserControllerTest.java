package id.ac.ui.cs.advprog.udehnihh.authentication.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.fromString("6b0db3f4-b10f-441a-b8b7-5d24a8e7994c"))
                .email("user@example.com")
                .fullName("User Example")
                .password("password123")
                .build();
    }

    @Test
    void testGetUserByIdReturnsUser() throws Exception {
        when(userService.getUserById(UUID.fromString("6b0db3f4-b10f-441a-b8b7-5d24a8e7994c"))).thenReturn(user);

        mockMvc.perform(get("/users/6b0db3f4-b10f-441a-b8b7-5d24a8e7994c"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"))
                .andExpect(jsonPath("$.fullName").value("User Example"));
    }
}
