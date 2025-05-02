package id.ac.ui.cs.advprog.udehnihh.authentication.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testShowLoginForm() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testShowRegisterForm() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testSuccessfulLogin() throws Exception {
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("123456");

        when(userService.authenticate("test@example.com", "123456")).thenReturn(mockUser);

        mockMvc.perform(post("/auth/login")
                        .param("email", "test@example.com")
                        .param("password", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/homepage"));
    }

    @Test
    void testFailedLogin() throws Exception {
        when(userService.authenticate("wrong@example.com", "wrongpass"))
                .thenThrow(new IllegalArgumentException("Email atau password salah"));

        mockMvc.perform(post("/auth/login")
                        .param("email", "wrong@example.com")
                        .param("password", "wrongpass"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testLogoutRedirect() throws Exception {
        mockMvc.perform(get("/auth/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
