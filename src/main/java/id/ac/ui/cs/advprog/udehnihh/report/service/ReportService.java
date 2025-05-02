package id.ac.ui.cs.advprog.udehnihh.report.service;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;

import java.util.List;

public interface ReportService {
    public Report createReport(Report report);
    public Report updateReport(String reportId, String title, String description);
    public void deleteReport(String id);
    public Report getReportById(String id);
    public List<Report> getReportsByAuthor(String authorId);
    public List<Report> getAllReports();


}