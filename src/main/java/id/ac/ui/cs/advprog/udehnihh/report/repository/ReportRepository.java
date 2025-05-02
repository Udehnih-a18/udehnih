package id.ac.ui.cs.advprog.udehnihh.report.repository;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ReportRepository {
    private List<Report> reportData = new ArrayList<>();

    public Report save(Report report) {
        int i = 0;
        for (Report savedReport : reportData) {
            if (savedReport.getIdReport().equals(report.getIdReport())) {
                reportData.remove(i);
                reportData.add(i, report);
                return report;
            }
            i++;
        }
        reportData.add(report);
        return report;
    }

    public List<Report> findAllByAuthor(String author) {
        List<Report> result = new ArrayList<>();
        for (Report report : reportData) {
            if (report.getAuthor().equals(author)) {
                result.add(report);
            }
        }
        return result;
    }

    public List<Report> findAll() {
        return reportData;
    }

    public Report findById(String id) {
        for (Report report : reportData) {
            if (report.getIdReport().equals(id)) {
                return report;
            }
        }
        return null;
    }

    public void delete(String id) {
        Report report = findById(id);
        reportData.remove(report);
    }
}
