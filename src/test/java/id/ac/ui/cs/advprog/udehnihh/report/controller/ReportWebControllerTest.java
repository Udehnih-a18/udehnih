package id.ac.ui.cs.advprog.udehnihh.report.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportWebController.class)
class ReportWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("✅ GET /student/reports returns student-reports-mainpage")
    void testStudentReportsPage() throws Exception {
        mockMvc.perform(get("/student/reports"))
                .andExpect(status().isOk())
                .andExpect(view().name("student-reports-mainpage"));
    }

    @Test
    @DisplayName("✅ GET /student/report/{id} returns student-detail-report")
    void testStudentReportDetailPage() throws Exception {
        mockMvc.perform(get("/student/report/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("student-detail-report"));
    }

    @Test
    @DisplayName("❌ GET /student/reports/ (with slash) returns 404")
    void testInvalidTrailingSlash() throws Exception {
        mockMvc.perform(get("/student/reports/"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("❌ GET /student/report (missing ID) returns 404")
    void testMissingId() throws Exception {
        mockMvc.perform(get("/student/report"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("❌ GET /student/unknown returns 404")
    void testInvalidStudentPath() throws Exception {
        mockMvc.perform(get("/student/unknown"))
                .andExpect(status().isNotFound());
    }
}