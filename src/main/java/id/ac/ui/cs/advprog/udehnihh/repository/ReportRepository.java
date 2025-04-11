package id.ac.ui.cs.advprog.udehnihh.repository;

import id.ac.ui.cs.advprog.udehnihh.model.Report;
import java.util.*;

public class ReportRepository {
    private final Map<String, Report> reports = new HashMap<>();

    public void create(Report report) {
        reports.put(report.getIdReport(), report);
    }

    public List<Report> findAll() {
        return new ArrayList<>(reports.values());
    }

    public Report findById(String id) {
        return reports.get(id);
    }

    public void update(String id, Report updatedReport) {
        reports.put(id, updatedReport);
    }

    public void delete(String id) {
        reports.remove(id);
    }
}
