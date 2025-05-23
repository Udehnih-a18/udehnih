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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;

    public ReportServiceImpl(ReportRepository repository) {
        this.repository = repository;
    }

    @Async("reportExecutor")
    public CompletableFuture<Report> createReport(Report report) {
        try {
            CreateReportCommand command = new CreateReportCommand(report, repository);
            Report result = command.execute();
            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async("reportExecutor")
    public CompletableFuture<Report> updateReport(String id, User user, String title, String desc) {
        try {
            UpdateReportCommand command = new UpdateReportCommand(id, user, title, desc, repository);
            Report result = command.execute();
            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }

    @Async("reportExecutor")
    public CompletableFuture<List<Report>> getAllReports() {
        try {
            GetAllReportsCommand command = new GetAllReportsCommand(repository);
            List<Report> result = command.executeAll();
            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
    @Override
    public Report getReportById(String id) {
        return new GetReportByIdCommand(id, repository).execute();
    }

    @Override
    public List<Report> getReportsByAuthor(User user) {
        return new GetReportsByAuthorCommand(user, repository).executeList();
    }

    @Override
    public void deleteReport(String id, User user) {
        new DeleteReportCommand(id, user, repository).execute();
    }

}