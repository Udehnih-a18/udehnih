package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;

public class CreateReportCommand implements ReportCommand {

    private final Report report;
    private final ReportRepository repository;

    public CreateReportCommand(Report report, ReportRepository repository) {
        this.report = report;
        this.repository = repository;
    }

    @Override
    public Report execute() {
        return repository.save(report);
    }
}