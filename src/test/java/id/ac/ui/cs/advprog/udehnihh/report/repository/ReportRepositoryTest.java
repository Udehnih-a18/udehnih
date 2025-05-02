package id.ac.ui.cs.advprog.udehnihh.report.repository;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ReportRepositoryTest {
    private ReportRepository reportRepository;

    List<Report> reports;

    UUID userUUID1 = UUID.randomUUID();
    UUID userUUID2 = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        reportRepository = new ReportRepository();

        reports = new ArrayList<>();
        Report report1 = new Report(userUUID1.toString(), "student1", "Title 1", "Description 1");
        reports.add(report1);
        Report report2 = new Report(userUUID2.toString(), "student2", "Title 2", "Description 2");
        reports.add(report2);

        // Add additional reports with same author for testing findAllByAuthor
        Report report3 = new Report(UUID.randomUUID().toString(), "student1", "Title 3", "Description 3");
        reports.add(report3);
    }

    @Test
    @DisplayName("Should save a report successfully")
    void testSaveReport() {
        Report report = reports.get(0);
        Report result = reportRepository.save(report);

        Report findResult = reportRepository.findById(reports.get(0).getIdReport());
        assertEquals(report.getIdReport(), result.getIdReport());
        assertEquals(report.getIdReport(), findResult.getIdReport());
        assertEquals(report.getTitle(), findResult.getTitle());
        assertEquals(report.getDescription(), findResult.getDescription());
        assertEquals(report.getCreatedBy(), findResult.getCreatedBy());
        assertEquals(report.getAuthor(), findResult.getAuthor());
    }

    @Test
    @DisplayName("Should update an existing report")
    void testSaveUpdate() {
        Report report = reports.get(1);
        reportRepository.save(report);
        Report newReport = new Report(report.getCreatedBy(), report.getAuthor() , report.getTitle(), report.getDescription());
        newReport.setIdReport(report.getIdReport());
        Report result = reportRepository.save(newReport);

        Report findResult = reportRepository.findById(reports.get(1).getIdReport());
        assertEquals(report.getIdReport(), result.getIdReport());
        assertEquals(report.getIdReport(), findResult.getIdReport());
        assertEquals(report.getCreatedBy(), findResult.getCreatedBy());
        assertEquals(report.getAuthor(), findResult.getAuthor());
        assertEquals(report.getTitle(), findResult.getTitle());
        assertEquals(report.getDescription(), findResult.getDescription());
    }

    @Test
    @DisplayName("Should delete a report successfully")
    void testDeleteReport() {
        Report report = reports.get(0);
        reportRepository.save(report);
        reportRepository.delete(report.getIdReport());
        Report findResult = reportRepository.findById(report.getIdReport());
        assertNull(findResult);
    }

    // New test for findAllByAuthor
    @Test
    @DisplayName("Should find all reports by specific author")
    void testFindAllByAuthor() {
        // Save all reports
        for (Report report : reports) {
            reportRepository.save(report);
        }

        // Find all reports by author "student1"
        List<Report> result = reportRepository.findAllByAuthor("student1");

        // Should find 2 reports (report1 and report3)
        assertEquals(2, result.size());

        // Verify they are the correct reports
        for (Report report : result) {
            assertEquals("student1", report.getAuthor());
        }
    }

    // Test finding reports by author that doesn't exist
    @Test
    @DisplayName("Should return empty list when author has no reports")
    void testFindAllByAuthorNoResults() {
        // Save all reports
        for (Report report : reports) {
            reportRepository.save(report);
        }

        // Find all reports by an author that doesn't exist
        List<Report> result = reportRepository.findAllByAuthor("nonexistent");

        // Should find 0 reports
        assertTrue(result.isEmpty());
    }

    // Test finding by ID that doesn't exist
    @Test
    @DisplayName("Should return null when report ID doesn't exist")
    void testFindByIdNotFound() {
        Report findResult = reportRepository.findById("nonexistent-id");
        assertNull(findResult);
    }

    // Test deleting a report that doesn't exist
    @Test
    @DisplayName("Should not throw exception when deleting non-existent report")
    void testDeleteNonExistentReport() {
        // Should not throw an exception
        assertDoesNotThrow(() -> reportRepository.delete("nonexistent-id"));
    }

    // Test saving multiple reports
    @Test
    @DisplayName("Should save multiple reports successfully")
    void testSaveMultipleReports() {
        // Save all reports
        for (Report report : reports) {
            reportRepository.save(report);
        }

        // Verify all reports were saved
        for (Report report : reports) {
            Report findResult = reportRepository.findById(report.getIdReport());
            assertNotNull(findResult);
            assertEquals(report.getIdReport(), findResult.getIdReport());
        }
    }

    // Test findAll method
    @Test
    @DisplayName("Should return all reports when findAll is called")
    void testFindAll() {
        // Initial state should be empty
        List<Report> initialResult = reportRepository.findAll();
        assertTrue(initialResult.isEmpty());

        // Save all reports
        for (Report report : reports) {
            reportRepository.save(report);
        }

        // Find all reports
        List<Report> result = reportRepository.findAll();

        // Should find all reports
        assertEquals(reports.size(), result.size());

        // All reports should be in the result
        for (Report report : reports) {
            boolean found = false;
            for (Report resultReport : result) {
                if (resultReport.getIdReport().equals(report.getIdReport())) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Report with ID " + report.getIdReport() + " was not found in the result");
        }
    }

    // Test findAll after deleting a report
    @Test
    @DisplayName("Should return correct reports after deletion")
    void testFindAllAfterDelete() {
        // Save all reports
        for (Report report : reports) {
            reportRepository.save(report);
        }

        // Delete one report
        Report reportToDelete = reports.get(0);
        reportRepository.delete(reportToDelete.getIdReport());

        // Find all reports
        List<Report> result = reportRepository.findAll();

        // Should find one less report
        assertEquals(reports.size() - 1, result.size());

        // The deleted report should not be in the result
        for (Report resultReport : result) {
            assertNotEquals(reportToDelete.getIdReport(), resultReport.getIdReport());
        }
    }

    // Test the order of reports in findAll
    @Test
    @DisplayName("Should maintain order of insertion in findAll")
    void testFindAllOrder() {
        // Create a specific order of reports
        Report report1 = new Report(UUID.randomUUID().toString(), "orderTest", "Title Order 1", "Description Order 1");
        Report report2 = new Report(UUID.randomUUID().toString(), "orderTest", "Title Order 2", "Description Order 2");
        Report report3 = new Report(UUID.randomUUID().toString(), "orderTest", "Title Order 3", "Description Order 3");

        // Save in specific order
        reportRepository.save(report1);
        reportRepository.save(report2);
        reportRepository.save(report3);

        // Get all reports
        List<Report> result = reportRepository.findAll();

        // Verify order is maintained
        assertEquals(3, result.size());
        assertEquals(report1.getIdReport(), result.get(0).getIdReport());
        assertEquals(report2.getIdReport(), result.get(1).getIdReport());
        assertEquals(report3.getIdReport(), result.get(2).getIdReport());
    }
}