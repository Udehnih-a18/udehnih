package id.ac.ui.cs.advprog.udehnihh.report.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    private Report report;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setFullName("Test User");

        report = Report.builder()
                .idReport("test-id")
                .createdBy(user)
                .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0))
                .updatedAt(LocalDateTime.of(2023, 1, 2, 0, 0))
                .title("Test Title")
                .description("Test Description")
                .build();
    }

    @Test
    void testGetterAndSetter() {
        assertEquals("test-id", report.getIdReport());
        assertEquals(user, report.getCreatedBy());
        assertEquals("Test Title", report.getTitle());
        assertEquals("Test Description", report.getDescription());

        LocalDateTime created = LocalDateTime.of(2022, 12, 31, 0, 0);
        report.setCreatedAt(created);
        assertEquals(created, report.getCreatedAt());

        LocalDateTime updated = LocalDateTime.of(2023, 12, 31, 0, 0);
        report.setUpdatedAt(updated);
        assertEquals(updated, report.getUpdatedAt());

        report.setIdReport("new-id");
        assertEquals("new-id", report.getIdReport());
    }

    @Test
    void testBuilder() {
        assertNotNull(report);
        assertEquals("Test Title", report.getTitle());
    }

    @Test
    void testNoArgsConstructor() {
        Report newReport = new Report();
        assertNull(newReport.getIdReport());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Report fullReport = new Report("123", user, now, now, "Judul", "Deskripsi");
        assertEquals("123", fullReport.getIdReport());
        assertEquals("Judul", fullReport.getTitle());
        assertEquals("Deskripsi", fullReport.getDescription());
    }

    @Test
    void testPrePersistSetsFields() {
        Report newReport = new Report();
        newReport.onCreate();

        assertNotNull(newReport.getIdReport());
        assertNotNull(newReport.getCreatedAt());
        assertNotNull(newReport.getUpdatedAt());
        assertEquals(newReport.getCreatedAt(), newReport.getUpdatedAt());
    }

    @Test
    void testPreUpdateSetsUpdatedAt() {
        LocalDateTime before = report.getUpdatedAt();
        report.onUpdate();
        assertTrue(report.getUpdatedAt().isAfter(before));
    }
}