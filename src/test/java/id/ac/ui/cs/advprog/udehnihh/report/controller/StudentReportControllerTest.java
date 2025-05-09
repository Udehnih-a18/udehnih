package id.ac.ui.cs.advprog.udehnihh.report.controller;

import id.ac.ui.cs.advprog.udehnihh.report.dto.ReportDTO;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class StudentReportControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private StudentReportController studentReportController;

    private static final String TEST_EMAIL = "student@example.com";
    private static final String TEST_REPORT_ID = "report123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(TEST_EMAIL);
    }

    @Test
    void testGetStudentReports_Success() {
        Report report1 = new Report(TEST_EMAIL, TEST_EMAIL, "Title 1", "Description 1");
        Report report2 = new Report(TEST_EMAIL, TEST_EMAIL, "Title 2", "Description 2");
        List<Report> expectedReports = Arrays.asList(report1, report2);

        when(reportService.getReportsByAuthor(TEST_EMAIL)).thenReturn(expectedReports);

        ResponseEntity<List<Report>> response = studentReportController.getStudentReports();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReports, response.getBody());
        verify(reportService).getReportsByAuthor(TEST_EMAIL);
    }

    @Test
    void testGetStudentReports_EmptyList() {
        List<Report> emptyList = List.of();
        when(reportService.getReportsByAuthor(TEST_EMAIL)).thenReturn(emptyList);

        ResponseEntity<List<Report>> response = studentReportController.getStudentReports();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyList, response.getBody());
        verify(reportService).getReportsByAuthor(TEST_EMAIL);
    }

    @Test
    void testCreateReport_Success() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setTitle("New Report");
        reportDTO.setDescription("Report Description");

        Report expectedReport = new Report(TEST_EMAIL, TEST_EMAIL, "New Report", "Report Description");
        when(reportService.createReport(any(Report.class))).thenReturn(expectedReport);

        ResponseEntity<Report> response = studentReportController.createReport(reportDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedReport, response.getBody());

        verify(reportService).createReport(any(Report.class));
    }

    @Test
    void testUpdateReport_Success() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setTitle("Updated Title");
        reportDTO.setDescription("Updated Description");

        Report updatedReport = new Report(TEST_EMAIL, TEST_EMAIL, "Updated Title", "Updated Description");
        when(reportService.updateReport(eq(TEST_REPORT_ID), eq(TEST_EMAIL), eq("Updated Title"), eq("Updated Description")))
                .thenReturn(updatedReport);

        ResponseEntity<Report> response = studentReportController.updateReport(TEST_REPORT_ID, reportDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedReport, response.getBody());

        verify(reportService).updateReport(TEST_REPORT_ID, TEST_EMAIL, "Updated Title", "Updated Description");
    }

    @Test
    void testDeleteReport_Success() {
        doNothing().when(reportService).deleteReport(TEST_REPORT_ID, TEST_EMAIL);

        ResponseEntity<?> response = studentReportController.deleteReport(TEST_REPORT_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reportService).deleteReport(TEST_REPORT_ID, TEST_EMAIL);
    }

    @Test
    void testUpdateReport_UnauthorizedUser() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setTitle("Updated Title");
        reportDTO.setDescription("Updated Description");

        when(reportService.updateReport(eq(TEST_REPORT_ID), eq(TEST_EMAIL), anyString(), anyString()))
                .thenThrow(new RuntimeException("User not authorized to update this report"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentReportController.updateReport(TEST_REPORT_ID, reportDTO);
        });

        assertEquals("User not authorized to update this report", exception.getMessage());
        verify(reportService).updateReport(eq(TEST_REPORT_ID), eq(TEST_EMAIL), anyString(), anyString());
    }

    @Test
    void testDeleteReport_UnauthorizedUser() {
        doThrow(new RuntimeException("User not authorized to delete this report"))
                .when(reportService).deleteReport(TEST_REPORT_ID, TEST_EMAIL);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentReportController.deleteReport(TEST_REPORT_ID);
        });

        assertEquals("User not authorized to delete this report", exception.getMessage());
        verify(reportService).deleteReport(TEST_REPORT_ID, TEST_EMAIL);
    }
}