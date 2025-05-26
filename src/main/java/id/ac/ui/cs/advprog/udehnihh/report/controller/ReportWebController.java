package id.ac.ui.cs.advprog.udehnihh.report.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class ReportWebController {

    @GetMapping("/reports")
    public String studentReportsPage() {
        return "student-reports-mainpage";
    }

    @GetMapping("/report-detail")
    public String reportDetailPage() {
        return "student-detail-report";
    }


}

