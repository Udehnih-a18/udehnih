package id.ac.ui.cs.advprog.udehnihh.report.controller;

import id.ac.ui.cs.advprog.udehnihh.report.dto.ReportDTO;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentReportController {

    @Autowired
    private ReportService reportService;

    // Student endpoint: Get all reports created by the authenticated student
    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getStudentReports() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<Report> reports = reportService.getReportsByAuthor(email);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    // Student endpoint: Create a new report
    @PostMapping("/reports")
    public ResponseEntity<Report> createReport(@RequestBody ReportDTO reportDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Report report = new Report(
                email,  // createdBy (user's email/ID)
                email,  // author (user's email/ID)
                reportDTO.getTitle(),
                reportDTO.getDescription()
        );

        Report createdReport = reportService.createReport(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
    }

    // Student endpoint: Update a report
    @PutMapping("/reports/{reportId}")
    public ResponseEntity<Report> updateReport(
            @PathVariable("reportId") String reportId,
            @RequestBody ReportDTO reportDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Report updatedReport = reportService.updateReport(
                reportId,
                email,
                reportDTO.getTitle(),
                reportDTO.getDescription()
        );

        return new ResponseEntity<>(updatedReport, HttpStatus.OK);
    }

    // Student endpoint: Delete a report
    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable("reportId") String reportId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        reportService.deleteReport(reportId, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}