package id.ac.ui.cs.advprog.udehnihh.report.controller;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/staff")
@PreAuthorize("hasRole('STAFF')")
public class StaffReportController {

    @Autowired
    private ReportService reportService;

    // Staff endpoint: Get all reports
    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    // Staff endpoint: Get report detail by ID
    @GetMapping("/reports/{reportId}")
    public ResponseEntity<Report> getReportDetail(@PathVariable("reportId") String reportId) {
        Report report = reportService.getReportById(reportId);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}