package id.ac.ui.cs.advprog.udehnihh.report.controller;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private StaffReportController staffReportController;

    private static final String TEST_REPORT_ID = "report123";
    private static final String STUDENT_EMAIL = "student@example.com";
    private static final String STAFF_EMAIL = "staff@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllReports_Success() {
        Report report1 = new Report(STUDENT_EMAIL, STUDENT_EMAIL, "Title 1", "Description 1");
        Report report2 = new Report(STUDENT_EMAIL, STUDENT_EMAIL, "Title 2", "Description 2");
        List<Report> expectedReports = Arrays.asList(report1, report2);

        when(reportService.getAllReports()).thenReturn(expectedReports);

        ResponseEntity<List<Report>> response = staffReportController.getAllReports();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReports, response.getBody());
        verify(reportService).getAllReports();
    }

    @Test
    void testGetAllReports_EmptyList() {
        List<Report> emptyList = List.of();
        when(reportService.getAllReports()).thenReturn(emptyList);

        ResponseEntity<List<Report>> response = staffReportController.getAllReports();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emptyList, response.getBody());
        verify(reportService).getAllReports();
    }

    @Test
    void testGetReportDetail_Success() {
        Report expectedReport = new Report(STUDENT_EMAIL, STUDENT_EMAIL, "Report Title", "Report Description");
        when(reportService.getReportById(TEST_REPORT_ID)).thenReturn(expectedReport);

        ResponseEntity<Report> response = staffReportController.getReportDetail(TEST_REPORT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedReport, response.getBody());
        verify(reportService).getReportById(TEST_REPORT_ID);
    }

    @Test
    void testGetReportDetail_ReportNotFound() {
        when(reportService.getReportById(TEST_REPORT_ID))
                .thenThrow(new RuntimeException("Report not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            staffReportController.getReportDetail(TEST_REPORT_ID);
        });

        assertEquals("Report not found", exception.getMessage());
        verify(reportService).getReportById(TEST_REPORT_ID);
    }
}