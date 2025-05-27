package id.ac.ui.cs.advprog.udehnihh.report.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student/reports")
public class ReportStudentWebController {

    @GetMapping("")
    public String studentReportsPage() {
        return "report/student-reports-mainpage";
    }

    @GetMapping("/report-detail")
    public String reportDetailPage() {
        return "report/detail-report";
    }

}

