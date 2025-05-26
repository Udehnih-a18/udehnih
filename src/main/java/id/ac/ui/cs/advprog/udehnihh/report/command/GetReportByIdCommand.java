package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;

import java.util.NoSuchElementException;

public class GetReportByIdCommand implements ReportCommand {
    private final String reportId;
    private final ReportRepository repository;

    public GetReportByIdCommand(String reportId, ReportRepository repository) {
        this.reportId = reportId;
        this.repository = repository;
    }

    @Override
    public Report execute() {
        return repository.findById(reportId).orElseThrow(() -> new NoSuchElementException("Report not found"));
    }
}