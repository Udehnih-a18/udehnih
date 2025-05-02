package id.ac.ui.cs.advprog.udehnihh.service;

import id.ac.ui.cs.advprog.udehnihh.model.Report;
import id.ac.ui.cs.advprog.udehnihh.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public Report createReport(Report report) {
        if (reportRepository.findById(report.getIdReport()) == null){
            reportRepository.save(report);
            return report;
        }
        return null;
    }

    @Override
    public Report getReportById(String id) {
        return reportRepository.findById(id);
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
    public Report updateReport(String reportId, String title, String description) {
        Report report = reportRepository.findById(reportId);
        if (report != null) {
            report.setTitle(title);
            report.setDescription(description);
            reportRepository.save(report);
            return report;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void deleteReport(String id) {
        reportRepository.delete(id);
    }
}