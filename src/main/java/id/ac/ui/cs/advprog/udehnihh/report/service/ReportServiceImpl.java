package id.ac.ui.cs.advprog.udehnihh.report.service;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.time.LocalDateTime;


@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public Report getReportById(String id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Report not found"));
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> getReportsByAuthor(String author) {
        return reportRepository.findAllByAuthor(author);
    }

    @Override
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public Report updateReport(String reportId, String username, String title, String description) {
        Optional<Report> optionalReport = reportRepository.findById(reportId);
        if (optionalReport.isEmpty()) {
            throw new NoSuchElementException("Report not found");
        }

        Report report = optionalReport.get();
        if (!report.getCreatedBy().equals(username)) {
            throw new AccessDeniedException("You can only update your own reports");
        }

        report.setTitle(title);
        report.setDescription(description);
        report.setUpdatedAt(LocalDateTime.now()); // Update the timestamp
        return reportRepository.save(report);
    }

    @Override
    public void deleteReport(String id, String username) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isEmpty()) {
            throw new NoSuchElementException("Report not found");
        }

        Report report = optionalReport.get();
        if (!report.getCreatedBy().equals(username)) {
            throw new AccessDeniedException("You can only delete your own reports");
        }

        reportRepository.deleteById(id);
    }
}