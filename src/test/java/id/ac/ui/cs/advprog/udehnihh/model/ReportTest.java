package id.ac.ui.cs.advprog.udehnihh.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    private List<Report> reports;

    @BeforeEach
    void setUp() {
        this.reports = new ArrayList<>();

        Report report1 = new Report("123", "Fitur tidak berjalan",
                "Fitur pada aplikasi ini tidak berjalan dengan benar dan cenderung rusak. Tolong diperbaiki!", "Zufar");

        Report report2 = new Report("456", "Course tidak tersimpan",
                "Fitur course pada aplikasi ini tidak berjalan dengan benar dan cenderung rusak. Tolong diperbaiki!", "Rafli");

        this.reports.add(report1);
        this.reports.add(report2);
    }

    @Test
    void createReportWithValidValues() {
        Report newReport = new Report("456", "Akhdan", "Ada bug di halaman utama.", "Baguss");

        assertEquals("456", newReport.getCreatedBy());
        assertEquals("Ada bug di halaman utama.", newReport.getTitle());
        assertEquals("Baguss", newReport.getDescription());
        assertEquals("Akhdan", newReport.getAuthor());
        assertNotNull(newReport.getIdReport());
        assertNotNull(newReport.getCreatedAt());

    }

    @Test
    void updateReportWithValidValues() {
        Report report = reports.get(0); // ambil report pertama (createdBy: "123")
        report.setTitle("Fitur berjalan!");
        report.setDescription("Update: Sudah diperbaiki di versi terbaru.");

        assertEquals("Fitur berjalan!", report.getTitle());
        assertEquals("Update: Sudah diperbaiki di versi terbaru.", report.getDescription());
    }

    @Test
    void createReportWithEmptyTitle() {
        Report newReport = new Report("abc", "Zufar", null, "Bagus");
        assertNull(newReport.getTitle());
    }

    @Test
    void createReportWithEmptyDescription() {
        Report newReport = new Report("abc", "Zufar", "Fitur keren", null);
        assertNull(newReport.getDescription());
    }
}
