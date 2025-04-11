package id.ac.ui.cs.advprog.udehnihh.repository;

import id.ac.ui.cs.advprog.udehnihh.model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportRepositoryTest {
    private ReportRepository reportRepository;

    @BeforeEach
    void setUp() {
        reportRepository = new ReportRepository();
    }

    @Test
    void testCreateReport() {
        Report report = new Report("student1", "student1", "Title", "Description");
        reportRepository.create(report);

        List<Report> reports = reportRepository.findAll();
        assertEquals(1, reports.size());
        assertEquals("Title", reports.get(0).getTitle());
    }

    @Test
    void testUpdateReport() {
        Report report = new Report("student1", "student1", "Initial", "Initial Desc");
        reportRepository.create(report);

        report.setTitle("Updated Title");
        report.setDescription("Updated Description");
        reportRepository.update(report.getIdReport(), report);

        Report updated = reportRepository.findById(report.getIdReport());
        assertEquals("Updated Title", updated.getTitle());
    }

    @Test
    void testDeleteReport() {
        Report report = new Report("student1", "student1", "Delete", "Desc");
        reportRepository.create(report);

        reportRepository.delete(report.getIdReport());
        assertTrue(reportRepository.findAll().isEmpty());
    }
}
