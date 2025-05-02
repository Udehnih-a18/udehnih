package id.ac.ui.cs.advprog.udehnihh.service;

import id.ac.ui.cs.advprog.udehnihh.model.Report;
import id.ac.ui.cs.advprog.udehnihh.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @InjectMocks
    ReportServiceImpl reportService;

    @Mock
    ReportRepository reportRepository;

    List<Report> reports;

    UUID userUUID1 = UUID.randomUUID();
    UUID userUUID2 = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        reports = new ArrayList<>();
        Report report1 = new Report(userUUID1.toString(), "student1", "Title 1", "Description 1");
        reports.add(report1);
        Report report2 = new Report(userUUID2.toString(), "student2", "Title 2", "Description 2");
        reports.add(report2);
    }

    @Test
    @DisplayName("Should create a report when it doesn't exist")
    void testCreateReport() {
        Report report = reports.get(0);
        when(reportRepository.findById(report.getIdReport())).thenReturn(null);
        when(reportRepository.save(report)).thenReturn(report);

        Report result = reportService.createReport(report);
        verify(reportRepository, times(1)).findById(report.getIdReport());
        verify(reportRepository, times(1)).save(report);
        assertEquals(report.getIdReport(), result.getIdReport());
    }

    @Test
    @DisplayName("Should return null when attempting to create a report that already exists")
    void testCreateReportIfAlreadyExists() {
        Report report = reports.get(0);
        when(reportRepository.findById(report.getIdReport())).thenReturn(report);

        assertNull(reportService.createReport(report));
        verify(reportRepository, times(1)).findById(report.getIdReport());
        verify(reportRepository, never()).save(report);
    }

    @Test
    @DisplayName("Should update a report successfully")
    void testUpdateReport() {
        Report report = reports.get(0);
        String newTitle = "New Title";

        when(reportRepository.findById(report.getIdReport())).thenReturn(report);
        when(reportRepository.save(any(Report.class))).thenReturn(report);

        Report result = reportService.updateReport(report.getIdReport(), newTitle, report.getDescription());

        verify(reportRepository, times(1)).findById(report.getIdReport());
        verify(reportRepository, times(1)).save(any(Report.class));

        assertEquals(newTitle, result.getTitle());
        assertEquals(report.getDescription(), result.getDescription());
        assertEquals(report.getAuthor(), result.getAuthor());
        assertEquals(report.getCreatedBy(), result.getCreatedBy());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when updating a non-existent report")
    void testUpdateReportNotFound() {
        String reportId = UUID.randomUUID().toString();
        String newTitle = "New Title";
        String newDescription = "New Description";

        when(reportRepository.findById(reportId)).thenReturn(null);

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> reportService.updateReport(reportId, newTitle, newDescription)
        );

        verify(reportRepository, times(1)).findById(reportId);
        verify(reportRepository, never()).save(any(Report.class));
    }

    @Test
    @DisplayName("Should get a report by ID successfully")
    void testGetReportById() {
        Report report = reports.get(0);
        when(reportRepository.findById(report.getIdReport())).thenReturn(report);

        Report result = reportService.getReportById(report.getIdReport());

        verify(reportRepository, times(1)).findById(report.getIdReport());
        assertEquals(report, result);
    }

    @Test
    @DisplayName("Should return null when getting a non-existent report")
    void testGetReportByIdNotFound() {
        String reportId = UUID.randomUUID().toString();
        when(reportRepository.findById(reportId)).thenReturn(null);

        Report result = reportService.getReportById(reportId);

        verify(reportRepository, times(1)).findById(reportId);
        assertNull(result);
    }

    @Test
    @DisplayName("Should delete a report successfully")
    void testDeleteReport() {
        String reportId = reports.get(0).getIdReport();
        doNothing().when(reportRepository).delete(reportId);

        reportService.deleteReport(reportId);

        verify(reportRepository, times(1)).delete(reportId);
    }

    @Test
    @DisplayName("Should handle deletion of non-existent report")
    void testDeleteReportNotFound() {
        String reportId = UUID.randomUUID().toString();

        // Just verify that the delete method is called
        // The service doesn't check if the report exists before deletion
        doNothing().when(reportRepository).delete(reportId);

        // This should not throw an exception
        assertDoesNotThrow(() -> reportService.deleteReport(reportId));

        verify(reportRepository, times(1)).delete(reportId);
    }

    @Test
    @DisplayName("Should update report with new title and description")
    void testUpdateReportWithNewTitleAndDescription() {
        Report report = reports.get(0);
        String newTitle = "Completely New Title";
        String newDescription = "Completely New Description";

        when(reportRepository.findById(report.getIdReport())).thenReturn(report);
        when(reportRepository.save(any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Report result = reportService.updateReport(report.getIdReport(), newTitle, newDescription);

        verify(reportRepository, times(1)).findById(report.getIdReport());
        verify(reportRepository, times(1)).save(any(Report.class));

        assertEquals(newTitle, result.getTitle());
        assertEquals(newDescription, result.getDescription());
        assertEquals(report.getAuthor(), result.getAuthor());
        assertEquals(report.getCreatedBy(), result.getCreatedBy());
    }
}