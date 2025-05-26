package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;

import java.util.List;

public class GetReportsByAuthorCommand implements ListReportCommand {
    private final User user;
    private final ReportRepository repository;

    public GetReportsByAuthorCommand(User user, ReportRepository repository) {
        this.user = user;
        this.repository = repository;
    }

    @Override
    public List<Report> execute() {
        return repository.findAllByCreatedBy(user);
    }
}