package id.ac.ui.cs.advprog.udehnihh.service;

import id.ac.ui.cs.advprog.udehnihh.model.Report;
import id.ac.ui.cs.advprog.udehnihh.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ReportServiceTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        ReportRepository reportRepository = new ReportRepository();
        reportService = new ReportService(reportRepository);
    }

    @Test
    void testCreateAndGetAllReports() {
        reportService.createReport("student1", "student1", "Title", "Description");

        List<Report> reports = reportService.getAllReports();
        assertEquals(1, reports.size());
    }

    @Test
    void testUpdateReport() {
        Report report = reportService.createReport("student1", "student1", "Old", "Old Desc");
        reportService.updateReport(report.getIdReport(), "New Title", "New Desc");

        Report updated = reportService.getReportById(report.getIdReport());
        assertEquals("New Title", updated.getTitle());
    }

    @Test
    void testDeleteReport() {
        Report report = reportService.createReport("student1", "student1", "ToDelete", "Desc");
        reportService.deleteReport(report.getIdReport());

        assertTrue(reportService.getAllReports().isEmpty());
    }
}
