package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetAllReportsCommandTest {

    private ReportRepository repository;
    private Report report;

    @BeforeEach
    void setUp() {
        repository = mock(ReportRepository.class);
        report = Report.builder()
                .idReport(UUID.randomUUID().toString())
                .title("All Reports")
                .description("Testing fetch all")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testExecuteAllReturnsReportList() {
        when(repository.findAll()).thenReturn(List.of(report));

        GetAllReportsCommand command = new GetAllReportsCommand(repository);
        List<Report> result = command.execute();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("All Reports", result.get(0).getTitle());
        verify(repository).findAll();
    }
}