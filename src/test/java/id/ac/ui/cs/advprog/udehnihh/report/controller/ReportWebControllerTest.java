package id.ac.ui.cs.advprog.udehnihh.report.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import id.ac.ui.cs.advprog.udehnihh.authentication.config.JwtAuthenticationFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class ReportWebControllerTest {

    private MockMvc mockMvc;

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private ReportWebController reportWebController;

    @BeforeEach
    void setUp() {
        reportWebController = new ReportWebController();

        mockMvc = MockMvcBuilders.standaloneSetup(reportWebController)
                .build();
    }

    @Test
    void testStudentReportsPage() throws Exception {
        mockMvc.perform(get("/student/reports"))
                .andExpect(status().isOk())
                .andExpect(view().name("student-reports-mainpage"));
    }

    @Test
    void testReportDetailPage() throws Exception {
        mockMvc.perform(get("/student/report-detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("student-detail-report"));
    }
}