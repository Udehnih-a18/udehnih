package id.ac.ui.cs.advprog.udehnihh.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.JwtService;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.TutorApplicationRequest;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication.ApplicationStatus;
import id.ac.ui.cs.advprog.udehnihh.course.service.TutorApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TutorApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TutorApplicationServiceImpl tutorApplicationService;

    @Mock
    private JwtService jwtService;

    private ObjectMapper objectMapper;
    private TutorApplicationController tutorApplicationController;

    private String token;
    private UUID userId;
    private User user;
    private TutorApplication application;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        tutorApplicationController = new TutorApplicationController(tutorApplicationService);

        mockMvc = MockMvcBuilders.standaloneSetup(tutorApplicationController)
                .build();

        userId = UUID.fromString("fcddf0e2-4faf-4952-8651-71a78c6840a9");
        user = new User();
        user.setId(userId);
        user.setFullName("Test User");
        user.setEmail("user@example.com");
        user.setRole(Role.STUDENT);
        token = "mock-jwt-token";

        application = new TutorApplication();
        application.setId(UUID.randomUUID());
        application.setApplicant(user);
        application.setMotivation("I love teaching");
        application.setExperience("3 years experience");
        application.setStatus(ApplicationStatus.PENDING);
        application.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testApplySuccess() throws Exception {
        TutorApplicationRequest request = new TutorApplicationRequest();
        request.setMotivation("I love teaching");
        request.setExperience("3 years experience");

        Mockito.when(tutorApplicationService.createApplication(Mockito.eq(token), Mockito.any()))
                .thenReturn(application);

        mockMvc.perform(post("/api/tutor-applications/apply")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicantName").value("Test User"))
                .andExpect(jsonPath("$.experience").value("3 years experience"))
                .andExpect(jsonPath("$.motivation").value("I love teaching"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void testGetApplicationStatusSuccess() throws Exception {
        Mockito.when(tutorApplicationService.getApplication(token))
                .thenReturn(application);
        mockMvc.perform(get("/api/tutor-applications/status")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicantId").value(userId.toString()))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void testDeleteApplicationSuccess() throws Exception {
        Mockito.doNothing().when(tutorApplicationService).deleteApplication(token);

        mockMvc.perform(delete("/api/tutor-applications/delete")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Application deleted successfully"));

        Mockito.verify(tutorApplicationService).deleteApplication(token);
    }
}