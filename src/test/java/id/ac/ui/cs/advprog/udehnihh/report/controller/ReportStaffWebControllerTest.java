package id.ac.ui.cs.advprog.udehnihh.report.controller;

import org.junit.jupiter.api.BeforeEach;
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
class ReportStaffWebControllerTest {

    private MockMvc mockMvc;

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private ReportStaffWebController reportStaffWebController;

    @BeforeEach
    void setUp() {
        reportStaffWebController = new ReportStaffWebController();

        mockMvc = MockMvcBuilders.standaloneSetup(reportStaffWebController)
                .build();
    }

    @Test
    void testStaffReportsMainpage() throws Exception {
        mockMvc.perform(get("/staff/reports"))
                .andExpect(status().isOk())
                .andExpect(view().name("report/staff-reports-mainpage"));
    }

    @Test
    void testReportDetailPage() throws Exception {
        mockMvc.perform(get("/staff/reports/report-detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("report/detail-report"));
    }
}
