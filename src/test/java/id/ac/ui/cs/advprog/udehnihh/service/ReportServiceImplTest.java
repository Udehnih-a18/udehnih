package id.ac.ui.cs.advprog.udehnihh.service;

import id.ac.ui.cs.advprog.udehnihh.model.Report;
import id.ac.ui.cs.advprog.udehnihh.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
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
    void testCreateReportIfAlreadyExists() {
        Report report = reports.get(0);
        when(reportRepository.findById(report.getIdReport())).thenReturn(report);

        assertNull(reportService.createReport(report));
        verify(reportRepository, times(1)).findById(report.getIdReport());
        verify(reportRepository, never()).save(report);
    }

    @Test
    void testUpdateReport() {
        Report report = reports.get(0);
        String newTitle = "New Title";

        when(reportRepository.findById(report.getIdReport())).thenReturn(report);

        Report result = reportService.updateReport(report.getIdReport(), newTitle, report.getDescription());

        verify(reportRepository, times(1)).findById(report.getIdReport());
        verify(reportRepository, times(1)).save(any(Report.class));

        assertEquals(newTitle, result.getTitle());
        assertEquals(report.getDescription(), result.getDescription());
        assertEquals(report.getAuthor(), result.getAuthor());
        assertEquals(report.getCreatedBy(), result.getCreatedBy());
    }
}