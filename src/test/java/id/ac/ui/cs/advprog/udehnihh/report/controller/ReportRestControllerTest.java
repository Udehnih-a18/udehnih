package id.ac.ui.cs.advprog.udehnihh.report.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.AuthService;
import id.ac.ui.cs.advprog.udehnihh.config.GlobalExceptionHandler;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReportRestControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private AuthService authService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ReportRestController reportRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User testUser;
    private Report testReport;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reportRestController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();

        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setEmail("test@example.com");
        testUser.setFullName("Test User");

        testReport = new Report();
        testReport.setIdReport("report-123");
        testReport.setTitle("Test Report");
        testReport.setDescription("Test Description");
        testReport.setCreatedBy(testUser);
    }


    // HAPPY PATH TESTS

    @Test
    void createReport_Success() throws Exception {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.createReport(any(Report.class))).thenReturn(testReport);

        Report requestReport = new Report();
        requestReport.setTitle("New Report");
        requestReport.setDescription("New Description");

        // Act & Assert
        mockMvc.perform(post("/api/student/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestReport))
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Report successfully created"))
                .andExpect(jsonPath("$.report.idReport").value("report-123"))
                .andExpect(jsonPath("$.report.title").value("Test Report"));

        verify(authService).getUserByEmail("test@example.com");
        verify(reportService).createReport(any(Report.class));
    }

    @Test
    void getStudentReports_Success() throws Exception {
        // Arrange
        List<Report> reports = Arrays.asList(testReport);
        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.getReportsByAuthor(testUser)).thenReturn(reports);

        // Act & Assert
        mockMvc.perform(get("/api/student/reports")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Reports fetched successfully"))
                .andExpect(jsonPath("$.reports").isArray())
                .andExpect(jsonPath("$.reports[0].idReport").value("report-123"));

        verify(authService).getUserByEmail("test@example.com");
        verify(reportService).getReportsByAuthor(testUser);
    }

    @Test
    void getStudentReportById_Success_WhenOwner() throws Exception {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.getReportById("report-123")).thenReturn(testReport);

        // Act & Assert
        mockMvc.perform(get("/api/student/reports/report-123")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Report fetched successfully"))
                .andExpect(jsonPath("$.report.idReport").value("report-123"));

        verify(authService).getUserByEmail("test@example.com");
        verify(reportService).getReportById("report-123");
    }

    @Test
    void updateStudentReport_Success() throws Exception {
        // Arrange
        Report updatedReport = new Report();
        updatedReport.setTitle("Updated Title");
        updatedReport.setDescription("Updated Description");

        Report returnedReport = new Report();
        returnedReport.setIdReport("report-123");
        returnedReport.setTitle("Updated Title");
        returnedReport.setDescription("Updated Description");
        returnedReport.setCreatedBy(testUser);

        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.updateReport("report-123", testUser, "Updated Title", "Updated Description"))
                .thenReturn(returnedReport);

        // Act & Assert
        mockMvc.perform(put("/api/student/reports/report-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedReport))
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Report successfully updated"))
                .andExpect(jsonPath("$.report.title").value("Updated Title"));

        verify(authService).getUserByEmail("test@example.com");
        verify(reportService).updateReport("report-123", testUser, "Updated Title", "Updated Description");
    }

    @Test
    void deleteStudentReport_Success() throws Exception {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        doNothing().when(reportService).deleteReport("report-123", testUser);

        // Act & Assert
        mockMvc.perform(delete("/api/student/reports/report-123")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Report successfully deleted"))
                .andExpect(jsonPath("$.reportId").value("report-123"));

        verify(authService).getUserByEmail("test@example.com");
        verify(reportService).deleteReport("report-123", testUser);
    }

    @Test
    void getAllReportsForStaff_Success() throws Exception {
        // Arrange
        List<Report> reports = Arrays.asList(testReport);
        when(reportService.getAllReports()).thenReturn(reports);

        // Act & Assert
        mockMvc.perform(get("/api/staff/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("All reports fetched successfully"))
                .andExpect(jsonPath("$.reports").isArray())
                .andExpect(jsonPath("$.reports[0].idReport").value("report-123"));

        verify(reportService).getAllReports();
    }

    @Test
    void getReportByIdForStaff_Success() throws Exception {
        // Arrange
        when(reportService.getReportById("report-123")).thenReturn(testReport);

        // Act & Assert
        mockMvc.perform(get("/api/staff/reports/report-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Report fetched successfully"))
                .andExpect(jsonPath("$.report.idReport").value("report-123"));

        verify(reportService).getReportById("report-123");
    }

    // UNHAPPY PATH TESTS

    @Test
    void createReport_ServiceThrowsException() throws Exception {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.createReport(any(Report.class)))
                .thenThrow(new RuntimeException("Service error"));

        Report requestReport = new Report();
        requestReport.setTitle("New Report");
        requestReport.setDescription("New Description");

        // Act & Assert
        mockMvc.perform(post("/api/student/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestReport))
                        .principal(authentication))
                .andExpect(status().isInternalServerError());

        verify(reportService).createReport(any(Report.class));
    }

    @Test
    void getStudentReports_ServiceThrowsException() throws Exception {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.getReportsByAuthor(testUser))
                .thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        mockMvc.perform(get("/api/student/reports")
                        .principal(authentication))
                .andExpect(status().isInternalServerError());

        verify(reportService).getReportsByAuthor(testUser);
    }

    @Test
    void getStudentReportById_AccessDenied_WhenNotOwner() throws Exception {
        // Arrange
        User anotherUser = new User();
        anotherUser.setId(UUID.randomUUID());
        anotherUser.setEmail("another@example.com");

        Report anotherUserReport = new Report();
        anotherUserReport.setIdReport("report-456");
        anotherUserReport.setCreatedBy(anotherUser);

        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.getReportById("report-456")).thenReturn(anotherUserReport);

        // Act & Assert
        mockMvc.perform(get("/api/student/reports/report-456")
                        .principal(authentication))
                .andExpect(status().isForbidden());

        verify(reportService).getReportById("report-456");
    }

    @Test
    void getStudentReportById_ReportNotFound() throws Exception {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.getReportById("nonexistent"))
                .thenThrow(new RuntimeException("Report not found"));

        // Act & Assert
        mockMvc.perform(get("/api/student/reports/nonexistent")
                        .principal(authentication))
                .andExpect(status().isInternalServerError());

        verify(reportService).getReportById("nonexistent");
    }

    @Test
    void updateStudentReport_ServiceThrowsException() throws Exception {
        // Arrange
        Report updatedReport = new Report();
        updatedReport.setTitle("Updated Title");
        updatedReport.setDescription("Updated Description");

        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.updateReport(anyString(), any(User.class), anyString(), anyString()))
                .thenThrow(new RuntimeException("Update failed"));

        // Act & Assert
        mockMvc.perform(put("/api/student/reports/report-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedReport))
                        .principal(authentication))
                .andExpect(status().isInternalServerError());

        verify(reportService).updateReport("report-123", testUser, "Updated Title", "Updated Description");
    }

    @Test
    void deleteStudentReport_ServiceThrowsException() throws Exception {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        doThrow(new RuntimeException("Delete failed"))
                .when(reportService).deleteReport("report-123", testUser);

        // Act & Assert
        mockMvc.perform(delete("/api/student/reports/report-123")
                        .principal(authentication))
                .andExpect(status().isInternalServerError());

        verify(reportService).deleteReport("report-123", testUser);
    }

    @Test
    void getAllReportsForStaff_ServiceThrowsException() throws Exception {
        // Arrange
        when(reportService.getAllReports())
                .thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        mockMvc.perform(get("/api/staff/reports"))
                .andExpect(status().isInternalServerError());

        verify(reportService).getAllReports();
    }

    @Test
    void getReportByIdForStaff_ReportNotFound() throws Exception {
        when(reportService.getReportById("nonexistent"))
                .thenThrow(new RuntimeException("Report not found"));

        mockMvc.perform(get("/api/staff/reports/nonexistent"))
                .andExpect(status().isInternalServerError());

        verify(reportService).getReportById("nonexistent");
    }


    @Test
    void createReport_AuthServiceThrowsException() throws Exception {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com"))
                .thenThrow(new RuntimeException("User not found"));

        Report requestReport = new Report();
        requestReport.setTitle("New Report");

        // Act & Assert
        mockMvc.perform(post("/api/student/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestReport))
                        .principal(authentication))
                .andExpect(status().isInternalServerError());

        verify(authService).getUserByEmail("test@example.com");
        verify(reportService, never()).createReport(any());
    }

    @Test
    void updateStudentReport_WithNullFields() throws Exception {
        // Arrange
        Report updatedReport = new Report();
        // Leave title and description as null

        Report returnedReport = new Report();
        returnedReport.setIdReport("report-123");
        returnedReport.setCreatedBy(testUser);

        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.updateReport("report-123", testUser, null, null))
                .thenReturn(returnedReport);

        // Act & Assert
        mockMvc.perform(put("/api/student/reports/report-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedReport))
                        .principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Report successfully updated"));

        verify(reportService).updateReport("report-123", testUser, null, null);
    }

    @Test
    void createReport_WithEmptyRequestBody() throws Exception {
        // Arrange
        when(authentication.getName()).thenReturn("test@example.com");
        when(authService.getUserByEmail("test@example.com")).thenReturn(testUser);
        when(reportService.createReport(any(Report.class))).thenReturn(testReport);

        // Act & Assert - Test with empty JSON object
        mockMvc.perform(post("/api/student/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .principal(authentication))
                .andExpect(status().isOk());

        verify(reportService).createReport(any(Report.class));
    }
}