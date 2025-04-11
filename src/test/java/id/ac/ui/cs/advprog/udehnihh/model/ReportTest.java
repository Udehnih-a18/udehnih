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
        Report newReport = new Report("456", "Bug UI", "Ada bug di halaman utama.", "Nayla");

        assertEquals("456", newReport.getCreatedBy());
        assertEquals("Bug UI", newReport.getTitle());
        assertEquals("Ada bug di halaman utama.", newReport.getDescription());
        assertEquals("Nayla", newReport.getAuthor());

        assertNotNull(newReport.getIdReport());
        assertNotNull(newReport.getCreatedAt());
        assertNotNull(newReport.getUpdatedAt());
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
        Report newReport = new Report("abc", "", "Deskripsi valid", "Zufar");
        assertTrue(newReport.getTitle().isEmpty(), "Title is unexpectedly not empty");
    }

    @Test
    void createReportWithEmptyDescription() {
        Report newReport = new Report("abc", "Masalah", null, "Zufar");
        assertNull(newReport.getDescription());
    }
}
