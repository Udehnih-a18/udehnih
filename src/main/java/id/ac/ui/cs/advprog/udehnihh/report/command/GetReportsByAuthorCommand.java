package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;

import java.util.List;

public class GetReportsByAuthorCommand implements ReportCommand {
    private final User user;
    private final ReportRepository repository;

    public GetReportsByAuthorCommand(User user, ReportRepository repository) {
        this.user = user;
        this.repository = repository;
    }

    @Override
    public Report execute() {
        throw new UnsupportedOperationException("Use executeList() instead");
    }

    public List<Report> executeList() {
        return repository.findAllByCreatedBy(user);
    }
}