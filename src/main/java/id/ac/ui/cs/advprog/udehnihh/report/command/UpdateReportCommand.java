package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class UpdateReportCommand implements ReportCommand {

    private final String reportId;
    private final User user;
    private final String newTitle;
    private final String newDescription;
    private final ReportRepository repository;

    public UpdateReportCommand(String reportId, User user, String newTitle, String newDescription, ReportRepository repository) {
        this.reportId = reportId;
        this.user = user;
        this.newTitle = newTitle;
        this.newDescription = newDescription;
        this.repository = repository;
    }

    @Override
    public Report execute() {
        Report report = repository.findById(reportId).orElseThrow(() -> new NoSuchElementException("Report not found"));
        if (!report.getCreatedBy().getId().equals(user.getId())) {
            throw new SecurityException("You can only update your own reports");
        }
        report.setTitle(newTitle);
        report.setDescription(newDescription);
        report.setUpdatedAt(LocalDateTime.now());
        return repository.save(report);
    }
}