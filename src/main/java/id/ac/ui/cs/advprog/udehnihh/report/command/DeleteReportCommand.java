package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

import java.util.NoSuchElementException;

public class DeleteReportCommand implements ReportCommand {

    private final String reportId;
    private final User user;
    private final ReportRepository repository;

    public DeleteReportCommand(String reportId, User user, ReportRepository repository) {
        this.reportId = reportId;
        this.user = user;
        this.repository = repository;
    }

    @Override
    public Report execute() {
        Report report = repository.findById(reportId).orElseThrow(() -> new NoSuchElementException("Report not found"));
        if (!report.getCreatedBy().getId().equals(user.getId())) {
            throw new SecurityException("You can only delete your own reports");
        }
        repository.deleteById(reportId);
        return report;
    }
}