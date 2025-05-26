
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
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportRestControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private ReportRestController reportRestController;

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
    void testCreateReport() {
        when(authService.getCurrentUser()).thenReturn(user);
        when(reportService.createReport(any(Report.class))).thenReturn(report);

        ResponseEntity<Map<String, Object>> response = reportRestController.createReport(report);
        assertEquals("Report successfully created", response.getBody().get("message"));
        verify(reportService).createReport(any(Report.class));
    }

    @Test
    void testGetStudentReports() {
        when(authService.getCurrentUser()).thenReturn(user);
        when(reportService.getReportsByAuthor(user)).thenReturn(List.of(report));

        ResponseEntity<Map<String, Object>> response = reportRestController.getStudentReports();
        assertEquals("Reports fetched successfully", response.getBody().get("message"));
        List<?> reports = (List<?>) response.getBody().get("reports");
        assertEquals(1, reports.size());
    }

    @Test
    void testGetStudentReportById_Success() {
        when(authService.getCurrentUser()).thenReturn(user);
        when(reportService.getReportById("1")).thenReturn(report);

        ResponseEntity<Map<String, Object>> response = reportRestController.getStudentReportById("1");
        assertEquals("Report fetched successfully", response.getBody().get("message"));
    }

    @Test
    void testGetStudentReportById_AccessDenied() {
        User otherUser = new User();
        otherUser.setId(UUID.randomUUID());
        report.setCreatedBy(otherUser);

        when(authService.getCurrentUser()).thenReturn(user);
        when(reportService.getReportById("1")).thenReturn(report);

        assertThrows(AccessDeniedException.class, () -> reportRestController.getStudentReportById("1"));
    }

    @Test
    void testUpdateStudentReport() {
        Report updated = new Report();
        updated.setTitle("Updated Title");
        updated.setDescription("Updated Description");

        when(authService.getCurrentUser()).thenReturn(user);
        when(reportService.updateReport(eq("1"), eq(user), anyString(), anyString())).thenReturn(report);

        ResponseEntity<Map<String, Object>> response = reportRestController.updateStudentReport("1", updated);
        assertEquals("Report successfully updated", response.getBody().get("message"));
    }

    @Test
    void testDeleteStudentReport() {
        when(authService.getCurrentUser()).thenReturn(user);
        doNothing().when(reportService).deleteReport("1", user);

        ResponseEntity<Map<String, Object>> response = reportRestController.deleteStudentReport("1");
        assertEquals("Report successfully deleted", response.getBody().get("message"));
        assertEquals("1", response.getBody().get("reportId"));
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