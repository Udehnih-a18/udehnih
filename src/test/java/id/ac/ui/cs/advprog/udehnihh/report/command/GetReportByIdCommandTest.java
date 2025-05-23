import id.ac.ui.cs.advprog.udehnihh.report.command.GetReportByIdCommand;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetReportByIdCommandTest {

    private ReportRepository repository;
    private Report report;

    @BeforeEach
    void setUp() {
        repository = mock(ReportRepository.class);
        report = Report.builder()
                .idReport(UUID.randomUUID().toString())
                .title("Sample Report")
                .description("Just a test")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testExecuteReturnsReportIfFound() {
        when(repository.findById(report.getIdReport())).thenReturn(Optional.of(report));

        GetReportByIdCommand command = new GetReportByIdCommand(report.getIdReport(), repository);
        Report result = command.execute();

        assertNotNull(result);
        assertEquals(report.getIdReport(), result.getIdReport());
    }

    @Test
    void testExecuteThrowsExceptionIfNotFound() {
        String notFoundId = UUID.randomUUID().toString();
        when(repository.findById(notFoundId)).thenReturn(Optional.empty());

        GetReportByIdCommand command = new GetReportByIdCommand(notFoundId, repository);
        assertThrows(NoSuchElementException.class, command::execute);
    }
}