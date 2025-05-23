package id.ac.ui.cs.advprog.udehnihh.report.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportServiceImplTest {

    private ReportRepository repository;
    private ReportServiceImpl service;
    private User user;
    private Report report;

    @BeforeEach
    void setUp() {
        repository = mock(ReportRepository.class);
        service = new ReportServiceImpl(repository);

        user = User.builder().id(UUID.randomUUID()).email("user@test.com").build();
        report = Report.builder()
                .idReport(UUID.randomUUID().toString())
                .createdBy(user)
                .title("Report Title")
                .description("Some description")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateReport() {
        when(repository.save(report)).thenReturn(report);
        Report result = service.createReport(report).join();
        assertEquals(report.getTitle(), result.getTitle());
        verify(repository).save(report);
    }

    @Test
    void testGetReportById() {
        when(repository.findById(report.getIdReport())).thenReturn(Optional.of(report));
        Report result = service.getReportById(report.getIdReport());
        assertEquals(report.getIdReport(), result.getIdReport());
    }

    @Test
    void testGetAllReports() {
        when(repository.findAll()).thenReturn(List.of(report));
        List<Report> result = service.getAllReports().join();
        assertEquals(1, result.size());
    }

    @Test
    void testGetReportsByAuthor() {
        when(repository.findAllByCreatedBy(user)).thenReturn(List.of(report));
        List<Report> result = service.getReportsByAuthor(user);
        assertEquals(1, result.size());
        assertEquals(user, result.get(0).getCreatedBy());
    }

    @Test
    void testUpdateReport() {
        when(repository.findById(report.getIdReport())).thenReturn(Optional.of(report));
        when(repository.save(any())).thenReturn(report);
        Report updated = service.updateReport(report.getIdReport(), user, "New Title", "New Desc").join();
        assertEquals("New Title", updated.getTitle());
    }

    @Test
    void testDeleteReport() {
        when(repository.findById(report.getIdReport())).thenReturn(Optional.of(report));
        assertDoesNotThrow(() -> service.deleteReport(report.getIdReport(), user));
        verify(repository).deleteById(report.getIdReport());
    }
}