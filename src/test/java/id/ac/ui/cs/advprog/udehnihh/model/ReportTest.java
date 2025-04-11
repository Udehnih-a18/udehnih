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
            Report report1 = new Report();
            report1.setReportId("123");
            report1.setReportTitle("Fitur tidak berjalan");
            report1.setReportDescription("Fitur pada aplikasi ini tidak berjalan dengan" +
                    "benar dan cenderung rusak. Tolong diperbaiki!");
            report1.setReportAuthor("Zufar");
            Report report2 = new Report();
            report2.setReportId("456");
            report2.setReportTitle("Course tidak tersimpan");
            report2.setReportDescription("Fitur course pada aplikasi ini tidak berjalan dengan" +
                    "benar dan cenderung rusak. Tolong diperbaiki!");
            report2.setReportAuthor("Rafli");
            this.reports.add(report1);
            this.reports.add(report2);
        }

        @Test
        void createReportWithValidValues() {
            Report newReport = new Report();
            newReport.setReportId("456");
            newReport.setReportTitle("Bug UI");
            newReport.setReportDescription("Ada bug di halaman utama.");
            newReport.setReportAuthor("Nayla");
            assertEquals("456", newReport.getReportId());
            assertEquals("Bug UI", newReport.getReportTitle());
            assertEquals("Ada bug di halaman utama.", newReport.getReportDescription());
            assertEquals("Nayla", newReport.getReportAuthor());
        }

        @Test
        void updateReportWithValidValues() {
            Report report = this.reports.get("123");
            report.setReportAuthor("Kuntum");
            report.setReportTitle("Fitur berjalan!");
            report.setReportDescription("Update: Sudah diperbaiki di versi terbaru.");
            assertEquals("Kuntum", report.getReportAuthor());
            assertEquals("Fitur berjalan!", report.getReportTitle());
            assertEquals("Update: Sudah diperbaiki di versi terbaru.", report.getReportDescription());
        }

        @Test
        void createReportWithEmptyTitle() {
            Report newReport = new Report();
            newReport.setReportId("789");
            newReport.setReportDescription("Deskripsi valid");
            newReport.setReportAuthor("Zufar");
            assertTrue(newReport.getReportTitle().isEmpty(), "Title is unexpectedly not empty");
        }

        @Test
        void createReportWithEmptyDescription() {
            Report newReport = new Report();
            newReport.setReportId("888");
            newReport.setReportTitle("Masalah");
            newReport.setReportDescription(null);
            newReport.setReportAuthor("Zufar");
            assertNull(newReport.getReportDescription());
        }

    }
