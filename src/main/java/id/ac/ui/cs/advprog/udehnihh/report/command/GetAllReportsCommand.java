package id.ac.ui.cs.advprog.udehnihh.report.command;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;

import java.util.List;

public class GetAllReportsCommand implements ReportCommand {
    private final ReportRepository repository;

    public GetAllReportsCommand(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Report execute() {
        throw new UnsupportedOperationException("Use executeAll() instead");
    }

    public List<Report> executeAll() {
        return repository.findAll();
    }
}