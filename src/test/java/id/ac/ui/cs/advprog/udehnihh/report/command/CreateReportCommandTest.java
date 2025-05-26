package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateReportCommandTest {

    @Mock
    private ReportRepository repository;

    @Mock
    private Report report;

    @Test
    public void testExecute_shouldThrowException_whenTitleIsNull() {
        when(report.getTitle()).thenReturn(null);
        CreateReportCommand command = new CreateReportCommand(report, repository);

        assertThrows(IllegalArgumentException.class, command::execute);
    }

    @Test
    public void testExecute_shouldThrowException_whenTitleIsEmpty() {
        when(report.getTitle()).thenReturn("   ");
        CreateReportCommand command = new CreateReportCommand(report, repository);

        assertThrows(IllegalArgumentException.class, command::execute);
    }

    @Test
    public void testExecute_shouldThrowException_whenDescriptionIsNull() {
        when(report.getTitle()).thenReturn("Valid Title");
        when(report.getDescription()).thenReturn(null);
        CreateReportCommand command = new CreateReportCommand(report, repository);

        assertThrows(IllegalArgumentException.class, command::execute);
    }

    @Test
    public void testExecute_shouldThrowException_whenDescriptionIsEmpty() {
        when(report.getTitle()).thenReturn("Valid Title");
        when(report.getDescription()).thenReturn("   ");
        CreateReportCommand command = new CreateReportCommand(report, repository);

        assertThrows(IllegalArgumentException.class, command::execute);
    }

    @Test
    public void testExecute_shouldReturnSavedReport_whenInputIsValid() {
        when(report.getTitle()).thenReturn("Valid Title");
        when(report.getDescription()).thenReturn("Valid Description");
        when(repository.save(report)).thenReturn(report);

        CreateReportCommand command = new CreateReportCommand(report, repository);
        Report result = command.execute();

        assertEquals(report, result);
        verify(repository, times(1)).save(report);
    }
}