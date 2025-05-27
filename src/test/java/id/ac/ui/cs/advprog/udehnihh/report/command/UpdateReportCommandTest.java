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

class UpdateReportCommandTest {

    private ReportRepository repository;
    private User user;
    private Report report;

    @BeforeEach
    void setUp() {
        repository = mock(ReportRepository.class);
        user = User.builder().id(UUID.randomUUID()).build();
        report = Report.builder()
                .idReport(UUID.randomUUID().toString())
                .title("Old Title")
                .description("Old Description")
                .createdBy(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testExecuteUpdatesReportSuccessfully() {
        when(repository.findById(report.getIdReport())).thenReturn(Optional.of(report));
        when(repository.save(any())).thenReturn(report);

        UpdateReportCommand command = new UpdateReportCommand(
                report.getIdReport(), user, "New Title", "New Description", repository);

        Report result = command.execute();
        assertEquals("New Title", result.getTitle());
        assertEquals("New Description", result.getDescription());
        verify(repository).save(report);
    }

    @Test
    void testExecuteThrowsSecurityExceptionForDifferentUser() {
        User anotherUser = User.builder().id(UUID.randomUUID()).build();
        when(repository.findById(report.getIdReport())).thenReturn(Optional.of(report));

        UpdateReportCommand command = new UpdateReportCommand(
                report.getIdReport(), anotherUser, "Fail", "Fail", repository);

        assertThrows(SecurityException.class, command::execute);
    }

    @Test
    void testExecuteThrowsIllegalArgumentExceptionIfTitleIsNull() {
        UpdateReportCommand command = new UpdateReportCommand(
                report.getIdReport(), user, null, "Some description", repository);
        assertThrows(IllegalArgumentException.class, command::execute);
    }

    @Test
    void testExecuteThrowsIllegalArgumentExceptionIfTitleIsEmpty() {
        UpdateReportCommand command = new UpdateReportCommand(
                report.getIdReport(), user, "   ", "Some description", repository);
        assertThrows(IllegalArgumentException.class, command::execute);
    }

    @Test
    void testExecuteThrowsNoSuchElementIfNotFound() {
        when(repository.findById("invalid-id")).thenReturn(Optional.empty());

        UpdateReportCommand command = new UpdateReportCommand(
                "invalid-id", user, "Fail", "Fail", repository);

        assertThrows(NoSuchElementException.class, command::execute);
    }
    @Test
    void testExecuteThrowsIllegalArgumentExceptionIfNewDescriptionIsNull() {
        UpdateReportCommand command = new UpdateReportCommand(
                "reportId123", user, "Valid Title", null, repository);
        assertThrows(IllegalArgumentException.class, command::execute);
    }

    @Test
    void testExecuteThrowsIllegalArgumentExceptionIfNewDescriptionIsEmpty() {
        UpdateReportCommand command = new UpdateReportCommand(
                "reportId123", user, "Valid Title", "   ", repository);
        assertThrows(IllegalArgumentException.class, command::execute);
    }

}