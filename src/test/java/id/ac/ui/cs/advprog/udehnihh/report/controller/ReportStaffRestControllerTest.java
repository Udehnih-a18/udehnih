package id.ac.ui.cs.advprog.udehnihh.report.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.AuthService;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportStaffRestControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private ReportStaffRestController reportRestController;

    private User user;
    private Report report;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setFullName("Test User");

        report = new Report();
        report.setIdReport("1");
        report.setTitle("Bug");
        report.setDescription("There is a bug");
        report.setCreatedBy(user);
        report.setCreatedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testGetAllReportsForStaff() {
        when(reportService.getAllReports()).thenReturn(List.of(report));

        ResponseEntity<Map<String, Object>> response = reportRestController.getAllReportsForStaff();
        assertEquals("All reports fetched successfully", response.getBody().get("message"));
        List<?> reports = (List<?>) response.getBody().get("reports");
        assertEquals(1, reports.size());
    }

    @Test
    void testGetReportByIdForStaff() {
        when(reportService.getReportById("1")).thenReturn(report);

        ResponseEntity<Map<String, Object>> response = reportRestController.getReportByIdForStaff("1");
        assertEquals("Report fetched successfully", response.getBody().get("message"));
    }
}
