package id.ac.ui.cs.advprog.udehnihh.repository;

import id.ac.ui.cs.advprog.udehnihh.model.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
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
    void testDeleteReport() {
        Report report = reports.get(0);
        reportRepository.delete(report.getIdReport());
        Report findResult = reportRepository.findById(report.getIdReport());
        assertNull(findResult);
    }
}
