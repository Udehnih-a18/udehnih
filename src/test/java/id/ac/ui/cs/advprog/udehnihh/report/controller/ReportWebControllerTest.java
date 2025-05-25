package id.ac.ui.cs.advprog.udehnihh.report.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import id.ac.ui.cs.advprog.udehnihh.authentication.config.JwtAuthenticationFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ReportWebController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReportWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock JwtAuthenticationFilter untuk menghindari error injection
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    @DisplayName("GET /student/reports should return student-reports-mainpage")
    void testStudentReportsPage() throws Exception {
        mockMvc.perform(get("/student/reports"))
                .andExpect(status().isOk())
                .andExpect(view().name("student-reports-mainpage"));
    }

    @Test
    @DisplayName("GET /student/report-detail should return student-detail-report")
    void testReportDetailPage() throws Exception {
        mockMvc.perform(get("/student/report-detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("student-detail-report"));
    }
}