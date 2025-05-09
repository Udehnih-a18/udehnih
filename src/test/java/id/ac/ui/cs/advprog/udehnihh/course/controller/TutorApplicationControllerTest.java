package id.ac.ui.cs.advprog.udehnihh.course.controller;

import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication.ApplicationStatus;
import id.ac.ui.cs.advprog.udehnihh.course.service.TutorApplicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TutorApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorApplicationService tutorApplicationService;

    private final String mockToken = "Bearer mock.jwt.token";

    @Test
    void testApplyTutorApplication() throws Exception {
        mockMvc.perform(post("/api/tutor-applications/apply")
                .header("Authorization", mockToken))
                .andExpect(status().isOk())
                .andExpect(content().string("Application submitted successfully"));

        Mockito.verify(tutorApplicationService).createApplication("mock.jwt.token");
    }

    @Test
    void testGetApplicationStatus() throws Exception {
        TutorApplication application = new TutorApplication();
        application.setId(UUID.randomUUID());
        application.setStatus(ApplicationStatus.PENDING);

        Mockito.when(tutorApplicationService.getApplication("mock.jwt.token"))
                .thenReturn(application);

        mockMvc.perform(get("/api/tutor-applications/status")
                .header("Authorization", mockToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void testDeleteApplication() throws Exception {
        mockMvc.perform(delete("/api/tutor-applications/delete")
                .header("Authorization", mockToken))
                .andExpect(status().isOk())
                .andExpect(content().string("Application deleted successfully"));

        Mockito.verify(tutorApplicationService).deleteApplication("mock.jwt.token");
    }
}