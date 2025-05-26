package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CreateReportCommandTest {

    private ReportRepository repository;
    private Report report;

    @BeforeEach
    void setUp() {
        repository = mock(ReportRepository.class);
        report = Report.builder()
                .idReport(UUID.randomUUID().toString())
                .title("Create Title")
                .description("Create Description")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testExecuteCreatesReportSuccessfully() {
        when(repository.save(report)).thenReturn(report);
        CreateReportCommand command = new CreateReportCommand(report, repository);
        Report result = command.execute();

        assertEquals(report.getTitle(), result.getTitle());
        verify(repository, times(1)).save(report);
    }
}