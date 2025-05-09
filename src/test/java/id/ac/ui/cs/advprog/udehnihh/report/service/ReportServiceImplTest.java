package id.ac.ui.cs.advprog.udehnihh.report.service;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @InjectMocks
    ReportServiceImpl reportService;

    @Mock
    ReportRepository reportRepository;

    List<Report> reports;

    String username1 = "student1";
    String username2 = "student2";

    @BeforeEach
    void setUp() {
        reports = new ArrayList<>();
        // First parameter of Report constructor is createdBy
        Report report1 = new Report(username1, "Student One", "Title 1", "Description 1");
        reports.add(report1);
        Report report2 = new Report(username2, "Student Two", "Title 2", "Description 2");
        reports.add(report2);
    }

    @Test
    @DisplayName("Should create a report successfully")
    void testCreateReport() {
        Report report = reports.get(0);
        when(reportRepository.save(report)).thenReturn(report);

        Report result = reportService.createReport(report);

        verify(reportRepository, times(1)).save(report);
        assertEquals(report.getIdReport(), result.getIdReport());
    }

    @Test
    @DisplayName("Should update a report successfully when user is the creator")
    void testUpdateReportAsCreator() {
        Report report = reports.get(0);
        String reportId = report.getIdReport();
        String newTitle = "New Title";
        String newDescription = "New Description";
        LocalDateTime beforeUpdate = report.getUpdatedAt();

        // Verify the setup - username1 should be the createdBy value
        assertEquals(username1, report.getCreatedBy(), "Test setup issue: createdBy should match username1");

        // Wait a moment to ensure updatedAt will be different
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // Ignore
        }

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        when(reportRepository.save(any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Report result = reportService.updateReport(reportId, username1, newTitle, newDescription);

        verify(reportRepository, times(1)).findById(reportId);
        verify(reportRepository, times(1)).save(any(Report.class));

        assertEquals(newTitle, result.getTitle());
        assertEquals(newDescription, result.getDescription());
        assertEquals(report.getAuthor(), result.getAuthor());
        assertEquals(report.getCreatedBy(), result.getCreatedBy());
        assertTrue(result.getUpdatedAt().isAfter(beforeUpdate), "Updated timestamp should be later than before");
    }

    @Test
    @DisplayName("Should throw AccessDeniedException when updating another user's report")
    void testUpdateReportAsNonCreator() {
        Report report = reports.get(0);
        String reportId = report.getIdReport();
        String newTitle = "New Title";
        String newDescription = "New Description";
        String differentUsername = "differentUser";

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        AccessDeniedException exception = assertThrows(
                AccessDeniedException.class,
                () -> reportService.updateReport(reportId, differentUsername, newTitle, newDescription)
        );

        verify(reportRepository, times(1)).findById(reportId);
        verify(reportRepository, never()).save(any(Report.class));
        assertEquals("You can only update your own reports", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when updating a non-existent report")
    void testUpdateReportNotFound() {
        String reportId = UUID.randomUUID().toString();
        String newTitle = "New Title";
        String newDescription = "New Description";

        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> reportService.updateReport(reportId, username1, newTitle, newDescription)
        );

        verify(reportRepository, times(1)).findById(reportId);
        verify(reportRepository, never()).save(any(Report.class));
        assertEquals("Report not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should get a report by ID successfully")
    void testGetReportById() {
        Report report = reports.get(0);
        String reportId = report.getIdReport();

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        Report result = reportService.getReportById(reportId);

        verify(reportRepository, times(1)).findById(reportId);
        assertEquals(report, result);
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when getting a non-existent report")
    void testGetReportByIdNotFound() {
        String reportId = UUID.randomUUID().toString();
        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> reportService.getReportById(reportId)
        );

        verify(reportRepository, times(1)).findById(reportId);
        assertEquals("Report not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete a report successfully when user is the creator")
    void testDeleteReportAsCreator() {
        Report report = reports.get(0);
        String reportId = report.getIdReport();

        // Verify the setup - username1 should be the createdBy value
        assertEquals(username1, report.getCreatedBy(), "Test setup issue: createdBy should match username1");

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));
        doNothing().when(reportRepository).deleteById(reportId);

        reportService.deleteReport(reportId, username1);

        verify(reportRepository, times(1)).findById(reportId);
        verify(reportRepository, times(1)).deleteById(reportId);
    }

    @Test
    @DisplayName("Should throw AccessDeniedException when deleting another user's report")
    void testDeleteReportAsNonCreator() {
        Report report = reports.get(0);
        String reportId = report.getIdReport();
        String differentUsername = "differentUser";

        when(reportRepository.findById(reportId)).thenReturn(Optional.of(report));

        AccessDeniedException exception = assertThrows(
                AccessDeniedException.class,
                () -> reportService.deleteReport(reportId, differentUsername)
        );

        verify(reportRepository, times(1)).findById(reportId);
        verify(reportRepository, never()).deleteById(reportId);
        assertEquals("You can only delete your own reports", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when deleting a non-existent report")
    void testDeleteReportNotFound() {
        String reportId = UUID.randomUUID().toString();

        when(reportRepository.findById(reportId)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> reportService.deleteReport(reportId, username1)
        );

        verify(reportRepository, times(1)).findById(reportId);
        verify(reportRepository, never()).deleteById(reportId);
        assertEquals("Report not found", exception.getMessage());
    }

    @Test
    @DisplayName("Should get all reports successfully")
    void testGetAllReports() {
        when(reportRepository.findAll()).thenReturn(reports);

        List<Report> result = reportService.getAllReports();

        verify(reportRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(reports, result);
    }

    @Test
    @DisplayName("Should get reports by author successfully")
    void testGetReportsByAuthor() {
        String author = "Student One";
        List<Report> authorReports = List.of(reports.get(0));

        when(reportRepository.findAllByAuthor(author)).thenReturn(authorReports);

        List<Report> result = reportService.getReportsByAuthor(author);

        verify(reportRepository, times(1)).findAllByAuthor(author);
        assertEquals(1, result.size());
        assertEquals(authorReports, result);
    }

    @Test
    @DisplayName("Should return empty list when no reports found for author")
    void testGetReportsByAuthorWhenNoneExist() {
        String author = "nonexistentAuthor";
        List<Report> emptyList = new ArrayList<>();

        when(reportRepository.findAllByAuthor(author)).thenReturn(emptyList);

        List<Report> result = reportService.getReportsByAuthor(author);

        verify(reportRepository, times(1)).findAllByAuthor(author);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when repository has no reports")
    void testGetAllReportsWhenEmpty() {
        List<Report> emptyList = new ArrayList<>();
        when(reportRepository.findAll()).thenReturn(emptyList);

        List<Report> result = reportService.getAllReports();

        verify(reportRepository, times(1)).findAll();
        assertTrue(result.isEmpty());
    }
}