package id.ac.ui.cs.advprog.udehnihh.report.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
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
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportRestControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private AuthService authService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ReportRestController reportRestController;

    private User user;
    private Report report;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        UUID userId = UUID.randomUUID();
        user = User.builder()
                .id(userId)
                .email("user@example.com")
                .fullName("Test User")
                .password("securepass")
                .role(Role.STUDENT)
                .build();
        report = Report.builder()
                .idReport("report-id")
                .title("Test Report")
                .description("Test Description")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(user)
                .build();

        when(authentication.getName()).thenReturn(user.getEmail());
        when(authService.getUserByEmail(user.getEmail())).thenReturn(user);
    }


    @Test
    public void testCreateReportSuccess() {
        when(reportService.createReport(any(Report.class)))
                .thenReturn(CompletableFuture.completedFuture(report));

        CompletableFuture<ResponseEntity<?>> responseFuture = reportRestController.createReport(report, authentication);
        ResponseEntity<?> response = responseFuture.join();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((String) ((java.util.Map<?, ?>) response.getBody()).get("message")).contains("successfully"));
    }

    @Test
    public void testGetStudentReportsSuccess() {
        when(reportService.getReportsByAuthor(user)).thenReturn(List.of(report));
        ResponseEntity<?> response = reportRestController.getStudentReports(authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((String) ((java.util.Map<?, ?>) response.getBody()).get("message")).contains("successfully"));
    }

    @Test
    public void testGetStudentReportByIdSuccess() {
        when(reportService.getReportById("report-id")).thenReturn(report);
        ResponseEntity<?> response = reportRestController.getStudentReportById("report-id", authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((String) ((java.util.Map<?, ?>) response.getBody()).get("message")).contains("successfully"));
    }

    @Test
    public void testGetStudentReportByIdAccessDenied() {
        User anotherUser = User.builder().id(UUID.randomUUID()).build();
        Report otherReport = Report.builder().idReport("other-id").createdBy(anotherUser).build();
        when(reportService.getReportById("other-id")).thenReturn(otherReport);

        assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> {
            reportRestController.getStudentReportById("other-id", authentication);
        });
    }


    @Test
    public void testUpdateReportSuccess() {
        when(reportService.updateReport(eq("report-id"), eq(user), any(), any()))
                .thenReturn(CompletableFuture.completedFuture(report));

        CompletableFuture<ResponseEntity<?>> responseFuture = reportRestController.updateStudentReport("report-id", report, authentication);
        ResponseEntity<?> response = responseFuture.join();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteReportSuccess() {
        doNothing().when(reportService).deleteReport("report-id", user);
        ResponseEntity<?> response = reportRestController.deleteStudentReport("report-id", authentication);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetAllReportsForStaff() {
        when(reportService.getAllReports()).thenReturn(CompletableFuture.completedFuture(List.of(report)));
        CompletableFuture<ResponseEntity<?>> responseFuture = reportRestController.getAllReportsForStaff();
        ResponseEntity<?> response = responseFuture.join();

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetReportByIdForStaff() {
        when(reportService.getReportById("report-id")).thenReturn(report);
        ResponseEntity<?> response = reportRestController.getReportByIdForStaff("report-id");

        assertEquals(200, response.getStatusCodeValue());
    }
}
