package id.ac.ui.cs.advprog.udehnihh.service;

import id.ac.ui.cs.advprog.udehnihh.model.Report;
import id.ac.ui.cs.advprog.udehnihh.repository.ReportRepository;

import java.util.List;

public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(String createdBy, String author, String title, String description) {
        // TODO: Implement method
        return null;
    }

    public List<Report> getAllReports() {
        // TODO: Implement method
        return null;
    }

    public Report getReportById(String id) {
        // TODO: Implement method
        return null;
    }

    public void updateReport(String id, String title, String description) {
        // TODO: Implement method
    }

    public void deleteReport(String id) {
        // TODO: Implement method
    }
}
