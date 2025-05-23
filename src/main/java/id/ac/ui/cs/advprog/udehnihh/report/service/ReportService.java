package id.ac.ui.cs.advprog.udehnihh.report.service;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ReportService {
    Report getReportById(String id);
    CompletableFuture<List<Report>> getAllReports();
    List<Report> getReportsByAuthor(User user);
    CompletableFuture<Report> createReport(Report report);
    CompletableFuture<Report> updateReport(String reportId, User user, String title, String description);
    void deleteReport(String id, User user);
}