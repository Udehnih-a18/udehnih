package id.ac.ui.cs.advprog.udehnihh.report.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ReportTest {

    private User dummyUser;

    @BeforeEach
    void setUp() {
        dummyUser = User.builder()
                .id(UUID.randomUUID())
                .email("testuser@example.com")
                .build();
    }

    @Test
    void testBuilderAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        Report report = Report.builder()
                .idReport("abc123")
                .title("Bug Report")
                .description("Ada bug di halaman dashboard")
                .createdAt(now)
                .updatedAt(now)
                .createdBy(dummyUser)
                .build();

        assertEquals("abc123", report.getIdReport());
        assertEquals("Bug Report", report.getTitle());
        assertEquals("Ada bug di halaman dashboard", report.getDescription());
        assertEquals(dummyUser, report.getCreatedBy());
        assertEquals(now, report.getCreatedAt());
        assertEquals(now, report.getUpdatedAt());
    }

    @Test
    void testPrePersistInitializesFields() {
        Report report = new Report();
        report.setCreatedBy(dummyUser);
        report.setTitle("Bug Report");
        report.setDescription("Testing pre-persist");

        report.onCreate();

        assertNotNull(report.getIdReport());
        assertNotNull(report.getCreatedAt());
        assertNotNull(report.getUpdatedAt());
        assertEquals(report.getCreatedAt(), report.getUpdatedAt());
    }

    @Test
    void testPreUpdateChangesUpdatedAtOnly() {
        Report report = new Report();
        report.setCreatedBy(dummyUser);
        report.setTitle("Bug Report");
        report.setDescription("Testing pre-update");
        report.onCreate(); // simulate initial save

        LocalDateTime initialCreatedAt = report.getCreatedAt();
        LocalDateTime initialUpdatedAt = report.getUpdatedAt();

        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status
        }

        report.onUpdate();

        assertEquals(initialCreatedAt, report.getCreatedAt());
        assertTrue(report.getUpdatedAt().isAfter(initialUpdatedAt));
    }
}