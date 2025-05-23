package id.ac.ui.cs.advprog.udehnihh.report.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.AuthService;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportRestController {

    private final ReportService reportService;
    private final AuthService authService;

    // STUDENT: Create a report
    @PostMapping("/student/reports")
    public ResponseEntity<?> createReport(@RequestBody Report report, Authentication authentication) {
        User user = authService.getUserByEmail(authentication.getName());
        report.setCreatedBy(user);
        Report created = reportService.createReport(report);
        return ResponseEntity.ok(Map.of("message", "Report successfully created", "report", created));
    }

    // STUDENT: Get all personal reports
    @GetMapping("/student/reports")
    public ResponseEntity<?> getStudentReports(Authentication authentication) {
        User user = authService.getUserByEmail(authentication.getName());
        List<Report> reports = reportService.getReportsByAuthor(user);
        return ResponseEntity.ok(Map.of("message", "Reports fetched successfully", "reports", reports));
    }

    // STUDENT: Get specific report by ID (if owner)
    @GetMapping("/student/reports/{id}")
    public ResponseEntity<?> getStudentReportById(@PathVariable String id, Authentication authentication) {
        User user = authService.getUserByEmail(authentication.getName());
        Report report = reportService.getReportById(id);
        if (!report.getCreatedBy().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can only view your own reports");
        }
        return ResponseEntity.ok(Map.of("message", "Report fetched successfully", "report", report));
    }

    // STUDENT: Update report (if owner)
    @PutMapping("/student/reports/{id}")
    public ResponseEntity<?> updateStudentReport(@PathVariable String id, @RequestBody Report updated, Authentication authentication) {
        User user = authService.getUserByEmail(authentication.getName());
        Report result = reportService.updateReport(id, user, updated.getTitle(), updated.getDescription());
        return ResponseEntity.ok(Map.of("message", "Report successfully updated", "report", result));
    }

    // STUDENT: Delete report (if owner)
    @DeleteMapping("/student/reports/{id}")
    public ResponseEntity<?> deleteStudentReport(@PathVariable String id, Authentication authentication) {
        User user = authService.getUserByEmail(authentication.getName());
        reportService.deleteReport(id, user);
        return ResponseEntity.ok(Map.of("message", "Report successfully deleted", "reportId", id));
    }

    // STAFF: View all reports
    @GetMapping("/staff/reports")
    public ResponseEntity<?> getAllReportsForStaff() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(Map.of("message", "All reports fetched successfully", "reports", reports));
    }

    // STAFF: View specific report by ID
    @GetMapping("/staff/reports/{id}")
    public ResponseEntity<?> getReportByIdForStaff(@PathVariable String id) {
        Report report = reportService.getReportById(id);
        return ResponseEntity.ok(Map.of("message", "Report fetched successfully", "report", report));
    }
}
package id.ac.ui.cs.advprog.udehnihh.report.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.AuthService;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportRestController {

    private final ReportService reportService;
    private final AuthService authService;

    // STUDENT: Create a report
    @PostMapping("/student/reports")
    public ResponseEntity<?> createReport(@RequestBody Report report, Authentication authentication) {
        User user = authService.getUserByEmail(authentication.getName());
        report.setCreatedBy(user);
        Report created = reportService.createReport(report);
        return ResponseEntity.ok(Map.of("message", "Report successfully created", "report", created));
    }

    // STUDENT: Get all personal reports
    @GetMapping("/student/reports")
    public ResponseEntity<?> getStudentReports(Authentication authentication) {
        User user = authService.getUserByEmail(authentication.getName());
        List<Report> reports = reportService.getReportsByAuthor(user);
        return ResponseEntity.ok(Map.of("message", "Reports fetched successfully", "reports", reports));
    }

    // STUDENT: Get specific report by ID (if owner)
    @GetMapping("/student/reports/{id}")
    public ResponseEntity<?> getStudentReportById(@PathVariable String id, Authentication authentication) {
        User user = authService.getUserByEmail(authentication.getName());
        Report report = reportService.getReportById(id);
        if (!report.getCreatedBy().getId().equals(user.getId())) {
            throw new AccessDeniedException("You can only view your own reports");
        }
        return ResponseEntity.ok(Map.of("message", "Report fetched successfully", "report", report));
    }

    // STUDENT: Update report (if owner)
    @PutMapping("/student/reports/{id}")
    public ResponseEntity<?> updateStudentReport(@PathVariable String id, @RequestBody Report updated, Authentication authentication) {
        User user = authService.getUserByEmail(authentication.getName());
        Report result = reportService.updateReport(id, user, updated.getTitle(), updated.getDescription());
        return ResponseEntity.ok(Map.of("message", "Report successfully updated", "report", result));
    }

    // STUDENT: Delete report (if owner)
    @DeleteMapping("/student/reports/{id}")
    public ResponseEntity<?> deleteStudentReport(@PathVariable String id, Authentication authentication) {
        User user = authService.getUserByEmail(authentication.getName());
        reportService.deleteReport(id, user);
        return ResponseEntity.ok(Map.of("message", "Report successfully deleted", "reportId", id));
    }

    // STAFF: View all reports
    @GetMapping("/staff/reports")
    public ResponseEntity<?> getAllReportsForStaff() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(Map.of("message", "All reports fetched successfully", "reports", reports));
    }

    // STAFF: View specific report by ID
    @GetMapping("/staff/reports/{id}")
    public ResponseEntity<?> getReportByIdForStaff(@PathVariable String id) {
        Report report = reportService.getReportById(id);
        return ResponseEntity.ok(Map.of("message", "Report fetched successfully", "report", report));
    }
}