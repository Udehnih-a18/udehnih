package id.ac.ui.cs.advprog.udehnihh.report.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import enums.Role;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ReportController reportController;

    private User studentUser;
    private User staffUser;
    private Report report;
    private List<Report> reports;
    private Map<String, String> validPayload;
    private Map<String, String> invalidPayload;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup student user
        studentUser = User.builder()
                .id(UUID.randomUUID())
                .email("student@example.com")
                .fullName("Student User")
                .role(Role.STUDENT)
                .build();

        // Setup staff user
        staffUser = User.builder()
                .id(UUID.randomUUID())
                .email("staff@example.com")
                .fullName("Staff User")
                .role(Role.STAFF)
                .build();

        // Setup report
        report = new Report(
                studentUser.getId().toString(),
                studentUser.getEmail(),
                "Test Report",
                "This is a test report description"
        );

        // Setup list of reports
        reports = new ArrayList<>();
        reports.add(report);

        // Setup valid payload
        validPayload = new HashMap<>();
        validPayload.put("title", "New Report");
        validPayload.put("description", "This is a new report");

        // Setup invalid payload
        invalidPayload = new HashMap<>();
        invalidPayload.put("title", "New Report");
        // Missing description
    }

    // STAFF ENDPOINT TESTS

    @Test
    void getAllReports_AsStaff_ReturnsAllReports() {
        // Given
        when(session.getAttribute("user")).thenReturn(staffUser);
        when(reportService.getAllReports()).thenReturn(reports);

        // When
        ResponseEntity<List<Report>> response = reportController.getAllReports(session);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).getAllReports();
    }

    @Test
    void getAllReports_AsStudent_ReturnsForbidden() {
        // Given
        when(session.getAttribute("user")).thenReturn(studentUser);

        // When
        ResponseEntity<List<Report>> response = reportController.getAllReports(session);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(reportService, never()).getAllReports();
    }

    @Test
    void getAllReports_NotLoggedIn_ReturnsForbidden() {
        // Given
        when(session.getAttribute("user")).thenReturn(null);

        // When
        ResponseEntity<List<Report>> response = reportController.getAllReports(session);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(reportService, never()).getAllReports();
    }

    @Test
    void getReportById_AsStaff_ReturnsReport() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(staffUser);
        when(reportService.getReportById(reportId)).thenReturn(report);

        // When
        ResponseEntity<Report> response = reportController.getReportById(reportId, session);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
        verify(reportService, times(1)).getReportById(reportId);
    }

    @Test
    void getReportById_ReportNotFound_ReturnsNotFound() {
        // Given
        String reportId = "non-existent-id";
        when(session.getAttribute("user")).thenReturn(staffUser);
        when(reportService.getReportById(reportId)).thenReturn(null);

        // When
        ResponseEntity<Report> response = reportController.getReportById(reportId, session);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
    }

    @Test
    void getReportById_AsStudent_ReturnsForbidden() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(studentUser);

        // When
        ResponseEntity<Report> response = reportController.getReportById(reportId, session);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(reportService, never()).getReportById(anyString());
    }

    @Test
    void getReportById_NotLoggedIn_ReturnsForbidden() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(null);

        // When
        ResponseEntity<Report> response = reportController.getReportById(reportId, session);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(reportService, never()).getReportById(anyString());
    }

    // STUDENT ENDPOINT TESTS

    @Test
    void getMyReports_AsStudent_ReturnsStudentReports() {
        // Given
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.getReportsByAuthor(studentUser.getEmail())).thenReturn(reports);

        // When
        ResponseEntity<List<Report>> response = reportController.getMyReports(session);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reports, response.getBody());
        verify(reportService, times(1)).getReportsByAuthor(studentUser.getEmail());
    }

    @Test
    void getMyReports_NotLoggedIn_ReturnsUnauthorized() {
        // Given
        when(session.getAttribute("user")).thenReturn(null);

        // When
        ResponseEntity<List<Report>> response = reportController.getMyReports(session);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(reportService, never()).getReportsByAuthor(anyString());
    }

    @Test
    void getMyReportById_AsStudent_ReturnsOwnReport() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.getReportById(reportId)).thenReturn(report);

        // When
        ResponseEntity<Report> response = reportController.getMyReportById(reportId, session);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(report, response.getBody());
        verify(reportService, times(1)).getReportById(reportId);
    }

    @Test
    void getMyReportById_ReportNotFound_ReturnsNotFound() {
        // Given
        String reportId = "non-existent-id";
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.getReportById(reportId)).thenReturn(null);

        // When
        ResponseEntity<Report> response = reportController.getMyReportById(reportId, session);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
    }

    @Test
    void getMyReportById_NotOwnReport_ReturnsForbidden() {
        // Given
        String reportId = report.getIdReport();
        User anotherStudent = User.builder()
                .id(UUID.randomUUID())
                .email("another@example.com")
                .fullName("Another Student")
                .role(Role.STUDENT)
                .build();

        when(session.getAttribute("user")).thenReturn(anotherStudent);
        when(reportService.getReportById(reportId)).thenReturn(report);

        // When
        ResponseEntity<Report> response = reportController.getMyReportById(reportId, session);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
    }

    @Test
    void getMyReportById_NotLoggedIn_ReturnsUnauthorized() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(null);

        // When
        ResponseEntity<Report> response = reportController.getMyReportById(reportId, session);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(reportService, never()).getReportById(anyString());
    }

    @Test
    void createReport_ValidPayload_ReturnsCreated() {
        // Given
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.createReport(any(Report.class))).thenAnswer(invocation -> {
            Report r = invocation.getArgument(0);
            return r;
        });

        // When
        ResponseEntity<Report> response = reportController.createReport(validPayload, session);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(validPayload.get("title"), response.getBody().getTitle());
        assertEquals(validPayload.get("description"), response.getBody().getDescription());
        verify(reportService, times(1)).createReport(any(Report.class));
    }

    @Test
    void createReport_InvalidPayload_ReturnsBadRequest() {
        // Given
        when(session.getAttribute("user")).thenReturn(studentUser);

        // When
        ResponseEntity<Report> response = reportController.createReport(invalidPayload, session);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(reportService, never()).createReport(any(Report.class));
    }

    @Test
    void createReport_ServiceReturnsNull_ReturnsInternalServerError() {
        // Given
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.createReport(any(Report.class))).thenReturn(null);

        // When
        ResponseEntity<Report> response = reportController.createReport(validPayload, session);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(reportService, times(1)).createReport(any(Report.class));
    }

    @Test
    void createReport_NotLoggedIn_ReturnsUnauthorized() {
        // Given
        when(session.getAttribute("user")).thenReturn(null);

        // When
        ResponseEntity<Report> response = reportController.createReport(validPayload, session);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(reportService, never()).createReport(any(Report.class));
    }

    @Test
    void updateReport_ValidPayload_ReturnsUpdatedReport() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.getReportById(reportId)).thenReturn(report);
        when(reportService.updateReport(eq(reportId), anyString(), anyString())).thenAnswer(invocation -> {
            String title = invocation.getArgument(1);
            String description = invocation.getArgument(2);
            report.setTitle(title);
            report.setDescription(description);
            return report;
        });

        // When
        ResponseEntity<Report> response = reportController.updateReport(reportId, validPayload, session);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(validPayload.get("title"), response.getBody().getTitle());
        assertEquals(validPayload.get("description"), response.getBody().getDescription());
        verify(reportService, times(1)).updateReport(reportId, validPayload.get("title"), validPayload.get("description"));
    }

    @Test
    void updateReport_ReportNotFound_ReturnsNotFound() {
        // Given
        String reportId = "non-existent-id";
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.getReportById(reportId)).thenReturn(null);

        // When
        ResponseEntity<Report> response = reportController.updateReport(reportId, validPayload, session);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
        verify(reportService, never()).updateReport(anyString(), anyString(), anyString());
    }

    @Test
    void updateReport_NotOwnReport_ReturnsForbidden() {
        // Given
        String reportId = report.getIdReport();
        User anotherStudent = User.builder()
                .id(UUID.randomUUID())
                .email("another@example.com")
                .fullName("Another Student")
                .role(Role.STUDENT)
                .build();

        when(session.getAttribute("user")).thenReturn(anotherStudent);
        when(reportService.getReportById(reportId)).thenReturn(report);

        // When
        ResponseEntity<Report> response = reportController.updateReport(reportId, validPayload, session);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
        verify(reportService, never()).updateReport(anyString(), anyString(), anyString());
    }

    @Test
    void updateReport_InvalidPayload_ReturnsBadRequest() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.getReportById(reportId)).thenReturn(report);

        // When
        ResponseEntity<Report> response = reportController.updateReport(reportId, invalidPayload, session);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
        verify(reportService, never()).updateReport(anyString(), anyString(), anyString());
    }

    @Test
    void updateReport_ServiceThrowsNoSuchElementException_ReturnsNotFound() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.getReportById(reportId)).thenReturn(report);
        when(reportService.updateReport(eq(reportId), anyString(), anyString())).thenThrow(new NoSuchElementException());

        // When
        ResponseEntity<Report> response = reportController.updateReport(reportId, validPayload, session);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
        verify(reportService, times(1)).updateReport(reportId, validPayload.get("title"), validPayload.get("description"));
    }

    @Test
    void updateReport_ServiceThrowsException_ReturnsInternalServerError() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.getReportById(reportId)).thenReturn(report);
        when(reportService.updateReport(eq(reportId), anyString(), anyString())).thenThrow(new RuntimeException());

        // When
        ResponseEntity<Report> response = reportController.updateReport(reportId, validPayload, session);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
        verify(reportService, times(1)).updateReport(reportId, validPayload.get("title"), validPayload.get("description"));
    }

    @Test
    void updateReport_NotLoggedIn_ReturnsUnauthorized() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(null);

        // When
        ResponseEntity<Report> response = reportController.updateReport(reportId, validPayload, session);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(reportService, never()).getReportById(anyString());
        verify(reportService, never()).updateReport(anyString(), anyString(), anyString());
    }

    @Test
    void deleteReport_OwnReport_ReturnsNoContent() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.getReportById(reportId)).thenReturn(report);
        doNothing().when(reportService).deleteReport(reportId);

        // When
        ResponseEntity<Void> response = reportController.deleteReport(reportId, session);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
        verify(reportService, times(1)).deleteReport(reportId);
    }

    @Test
    void deleteReport_ReportNotFound_ReturnsNotFound() {
        // Given
        String reportId = "non-existent-id";
        when(session.getAttribute("user")).thenReturn(studentUser);
        when(reportService.getReportById(reportId)).thenReturn(null);

        // When
        ResponseEntity<Void> response = reportController.deleteReport(reportId, session);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
        verify(reportService, never()).deleteReport(anyString());
    }

    @Test
    void deleteReport_NotOwnReport_ReturnsForbidden() {
        // Given
        String reportId = report.getIdReport();
        User anotherStudent = User.builder()
                .id(UUID.randomUUID())
                .email("another@example.com")
                .fullName("Another Student")
                .role(Role.STUDENT)
                .build();

        when(session.getAttribute("user")).thenReturn(anotherStudent);
        when(reportService.getReportById(reportId)).thenReturn(report);

        // When
        ResponseEntity<Void> response = reportController.deleteReport(reportId, session);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        verify(reportService, times(1)).getReportById(reportId);
        verify(reportService, never()).deleteReport(anyString());
    }

    @Test
    void deleteReport_NotLoggedIn_ReturnsUnauthorized() {
        // Given
        String reportId = report.getIdReport();
        when(session.getAttribute("user")).thenReturn(null);

        // When
        ResponseEntity<Void> response = reportController.deleteReport(reportId, session);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(reportService, never()).getReportById(anyString());
        verify(reportService, never()).deleteReport(anyString());
    }
}
