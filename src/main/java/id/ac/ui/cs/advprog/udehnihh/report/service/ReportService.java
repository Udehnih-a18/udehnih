package id.ac.ui.cs.advprog.udehnihh.report.service;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import java.util.List;

public interface ReportService {
    Report getReportById(String id);
    List<Report> getAllReports();
    List<Report> getReportsByAuthor(String author);
    Report createReport(Report report);
    Report updateReport(String reportId, String username, String title, String description);
    void deleteReport(String id, String username);
}