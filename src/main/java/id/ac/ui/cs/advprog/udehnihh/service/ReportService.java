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
        Report report = new Report(createdBy, author, title, description);
        reportRepository.create(report);
        return report;
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReportById(String id) {
        return reportRepository.findById(id);
    }

    public void updateReport(String id, String title, String description) {
        Report report = reportRepository.findById(id);
        if (report != null) {
            report.setTitle(title);
            report.setDescription(description);
            reportRepository.update(id, report);
        }
    }

    public void deleteReport(String id) {
        reportRepository.delete(id);
    }
}