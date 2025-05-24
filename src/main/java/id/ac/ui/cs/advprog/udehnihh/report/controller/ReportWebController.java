package id.ac.ui.cs.advprog.udehnihh.report.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportWebController {

    @GetMapping("/student/reports")
    public String studentReportsPage() {
        return "student-reports-mainpage";
    }

    @GetMapping("/student/report-detail")
    public String reportDetailPage() {
        return "student-detail-report";
    }


}

