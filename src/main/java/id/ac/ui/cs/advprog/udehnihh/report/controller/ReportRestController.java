package id.ac.ui.cs.advprog.udehnihh.report.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.AuthService;
import id.ac.ui.cs.advprog.udehnihh.report.dto.ReportDto;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReportRestController {

    private final ReportService reportService;
    private final AuthService authService;

    private ReportDto toDto(Report report) {
        return new ReportDto(
                report.getIdReport(),
                report.getTitle(),
                report.getDescription(),
                report.getCreatedBy().getId().toString(),
                report.getCreatedBy().getEmail(),
                report.getCreatedBy().getFullName(),
                report.getCreatedAt(),
                report.getUpdatedAt()
        );
    }

    @PostMapping("/student/reports")
    public ResponseEntity<Map<String, Object>> createReport(@RequestBody Report report) {
        User currentUser = authService.getCurrentUser();
        report.setCreatedBy(currentUser);
        Report created = reportService.createReport(report);
        return ResponseEntity.ok(Map.of(
                "message", "Report successfully created",
                "report", toDto(created)
        ));
    }

    @GetMapping("/student/reports")
    public ResponseEntity<Map<String, Object>> getStudentReports() {
        User currentUser = authService.getCurrentUser();
        List<Report> reports = reportService.getReportsByAuthor(currentUser);
        List<ReportDto> dtos = reports.stream()
                .map(this::toDto)
                .toList(); // Java 16+
        return ResponseEntity.ok(Map.of(
                "message", "Reports fetched successfully",
                "reports", dtos
        ));
    }

    @GetMapping("/student/reports/{id}")
    public ResponseEntity<Map<String, Object>> getStudentReportById(@PathVariable String id) {
        User currentUser = authService.getCurrentUser();
        Report report = reportService.getReportById(id);
        if (!report.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You can only view your own reports");
        }
        return ResponseEntity.ok(Map.of(
                "message", "Report fetched successfully",
                "report", toDto(report)
        ));
    }

    @PutMapping("/student/reports/{id}")
    public ResponseEntity<Map<String, Object>> updateStudentReport(@PathVariable String id, @RequestBody Report updated) {
        User currentUser = authService.getCurrentUser();
        Report result = reportService.updateReport(id, currentUser, updated.getTitle(), updated.getDescription());
        return ResponseEntity.ok(Map.of(
                "message", "Report successfully updated",
                "report", toDto(result)
        ));
    }

    @DeleteMapping("/student/reports/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudentReport(@PathVariable String id) {
        User currentUser = authService.getCurrentUser();
        reportService.deleteReport(id, currentUser);
        return ResponseEntity.ok(Map.of(
                "message", "Report successfully deleted",
                "reportId", id
        ));
    }

    @GetMapping("/staff/reports")
    public ResponseEntity<Map<String, Object>> getAllReportsForStaff() {
        List<Report> reports = reportService.getAllReports();
        List<ReportDto> dtos = reports.stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(Map.of(
                "message", "All reports fetched successfully",
                "reports", dtos
        ));
    }

    @GetMapping("/staff/reports/{id}")
    public ResponseEntity<Map<String, Object>> getReportByIdForStaff(@PathVariable String id) {
        Report report = reportService.getReportById(id);
        return ResponseEntity.ok(Map.of(
                "message", "Report fetched successfully",
                "report", toDto(report)
        ));
    }
}