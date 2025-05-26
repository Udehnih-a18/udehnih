package id.ac.ui.cs.advprog.udehnihh.report.service;

import id.ac.ui.cs.advprog.udehnihh.report.command.CreateReportCommand;
import id.ac.ui.cs.advprog.udehnihh.report.command.UpdateReportCommand;
import id.ac.ui.cs.advprog.udehnihh.report.command.DeleteReportCommand;
import id.ac.ui.cs.advprog.udehnihh.report.command.GetAllReportsCommand;
import id.ac.ui.cs.advprog.udehnihh.report.command.GetReportsByAuthorCommand;
import id.ac.ui.cs.advprog.udehnihh.report.command.GetReportByIdCommand;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;

    public ReportServiceImpl(ReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Report getReportById(String id) {
        return new GetReportByIdCommand(id, repository).execute();
    }

    @Override
    public List<Report> getAllReports() {
        return new GetAllReportsCommand(repository).executeAll();
    }

    @Override
    public List<Report> getReportsByAuthor(User user) {
        return new GetReportsByAuthorCommand(user, repository).executeList();
    }

    @Override
    public Report createReport(Report report) {
        return new CreateReportCommand(report, repository).execute();
    }

    @Override
    public Report updateReport(String reportId, User user, String title, String description) {
        return new UpdateReportCommand(reportId, user, title, description, repository).execute();
    }

    @Override
    public void deleteReport(String id, User user) {
        new DeleteReportCommand(id, user, repository).execute();
    }
}