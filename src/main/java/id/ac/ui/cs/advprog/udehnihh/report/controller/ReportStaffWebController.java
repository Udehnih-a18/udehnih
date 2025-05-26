package id.ac.ui.cs.advprog.udehnihh.report.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff/reports")
public class ReportStaffWebController {

    @GetMapping("")
    public String studentReportsPage() {
        return "report/staff-reports-mainpage";
    }

    @GetMapping("/report-detail")
    public String reportDetailPage() {
        return "report/detail-report";
    }

}

