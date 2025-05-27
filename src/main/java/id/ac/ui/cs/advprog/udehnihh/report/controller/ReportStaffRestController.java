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
@RequestMapping("/api/staff/reports")
@RequiredArgsConstructor
public class ReportStaffRestController {

    private final ReportService reportService;
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


    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllReportsForStaff() {
        List<Report> reports = reportService.getAllReports();
        List<ReportDto> dtos = reports.stream()
                .map(this::toReportResponse)
                .toList();
        return ResponseEntity.ok(Map.of(
                MESSAGE, "All reports fetched successfully",
                REPORTS, dtos
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getReportByIdForStaff(@PathVariable String id) {
        Report report = reportService.getReportById(id);
        return ResponseEntity.ok(Map.of(
                MESSAGE, "Report fetched successfully",
                REPORT, toReportResponse(report)
        ));
    }
}
