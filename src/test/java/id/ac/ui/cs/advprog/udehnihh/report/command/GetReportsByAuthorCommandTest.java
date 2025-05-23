import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.report.command.GetReportsByAuthorCommand;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetReportsByAuthorCommandTest {

    private ReportRepository repository;
    private User user;
    private Report report;

    @BeforeEach
    void setUp() {
        repository = mock(ReportRepository.class);
        user = User.builder().id(UUID.randomUUID()).email("test@author.com").build();
        report = Report.builder()
                .idReport(UUID.randomUUID().toString())
                .title("Report by Author")
                .description("Filter by user")
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testExecuteListReturnsReportsOfSpecificAuthor() {
        when(repository.findAllByCreatedBy(user)).thenReturn(List.of(report));

        GetReportsByAuthorCommand command = new GetReportsByAuthorCommand(user, repository);
        List<Report> result = command.executeList();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user, result.get(0).getCreatedBy());
        verify(repository).findAllByCreatedBy(user);
    }
}