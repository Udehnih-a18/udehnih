package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
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

public class DeleteReportCommandTest {

    private ReportRepository repository;
    private User user;
    private Report report;

    @BeforeEach
    void setUp() {
        repository = mock(ReportRepository.class);
        user = User.builder().id(UUID.randomUUID()).build();
        report = Report.builder()
                .idReport(UUID.randomUUID().toString())
                .title("Delete Me")
                .description("Please delete")
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testExecuteDeletesReportSuccessfully() {
        when(repository.findById(report.getIdReport())).thenReturn(Optional.of(report));
        DeleteReportCommand command = new DeleteReportCommand(report.getIdReport(), user, repository);

        Report result = command.execute();
        assertEquals(report, result);
        verify(repository).deleteById(report.getIdReport());
    }

    @Test
    void testExecuteThrowsSecurityExceptionIfUserIsNotOwner() {
        User anotherUser = User.builder().id(UUID.randomUUID()).build();
        when(repository.findById(report.getIdReport())).thenReturn(Optional.of(report));

        DeleteReportCommand command = new DeleteReportCommand(report.getIdReport(), anotherUser, repository);
        assertThrows(SecurityException.class, command::execute);
    }

    @Test
    void testExecuteThrowsNoSuchElementIfReportNotFound() {
        when(repository.findById("invalid-id")).thenReturn(Optional.empty());

        DeleteReportCommand command = new DeleteReportCommand("invalid-id", user, repository);
        assertThrows(NoSuchElementException.class, command::execute);
    }
}