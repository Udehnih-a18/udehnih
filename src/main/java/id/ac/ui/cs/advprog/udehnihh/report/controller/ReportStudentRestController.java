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
@RequestMapping("/api/student/reports")
@RequiredArgsConstructor
public class ReportStudentRestController {

    private final ReportService reportService;
    private final AuthService authService;
    private static final String MESSAGE = "message";
    private static final String REPORT = "report";
    private static final String REPORTS = "reports";


    private ReportDto toReportResponse(Report report) {
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

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createReport(@RequestBody Report report) {
        User currentUser = authService.getCurrentUser();
        report.setCreatedBy(currentUser);
        Report created = reportService.createReport(report);
        return ResponseEntity.ok(Map.of(
                MESSAGE, "Report successfully created",
                REPORT, toReportResponse(created)
        ));
    }

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getStudentReports() {
        User currentUser = authService.getCurrentUser();
        List<Report> reports = reportService.getReportsByAuthor(currentUser);
        List<ReportDto> dtos = reports.stream()
                .map(this::toReportResponse)
                .toList(); // Java 16+
        return ResponseEntity.ok(Map.of(
                MESSAGE, "Reports fetched successfully",
                REPORTS, dtos
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getStudentReportById(@PathVariable String id) {
        User currentUser = authService.getCurrentUser();
        Report report = reportService.getReportById(id);
        if (!report.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You can only view your own reports");
        }
        return ResponseEntity.ok(Map.of(
                MESSAGE, "Report fetched successfully",
                REPORT, toReportResponse(report)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateStudentReport(@PathVariable String id, @RequestBody Report updated) {
        User currentUser = authService.getCurrentUser();
        Report result = reportService.updateReport(id, currentUser, updated.getTitle(), updated.getDescription());
        return ResponseEntity.ok(Map.of(
                MESSAGE, "Report successfully updated",
                REPORT, toReportResponse(result)
        ));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudentReport(@PathVariable String id) {
        User currentUser = authService.getCurrentUser();
        reportService.deleteReport(id, currentUser);
        return ResponseEntity.ok(Map.of(
                MESSAGE, "Report successfully deleted",
                "reportId", id
        ));
    }
}