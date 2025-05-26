package id.ac.ui.cs.advprog.udehnihh.report.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ReportDtoTest {

    @Test
    void testAllArgsConstructorAndGetters_HappyPath() {
        LocalDateTime now = LocalDateTime.now();

        ReportDto dto = new ReportDto(
                "r001",
                "Bug on dashboard",
                "There is a bug when loading chart.",
                "u001",
                "user@example.com",
                "User One",
                now,
                now
        );

        assertEquals("r001", dto.getIdReport());
        assertEquals("Bug on dashboard", dto.getTitle());
        assertEquals("There is a bug when loading chart.", dto.getDescription());
        assertEquals("u001", dto.getAuthorId());
        assertEquals("user@example.com", dto.getAuthorEmail());
        assertEquals("User One", dto.getAuthorFullName());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void testNoArgsConstructorAndSetters_HappyPath() {
        ReportDto dto = new ReportDto();
        LocalDateTime now = LocalDateTime.now();

        dto.setIdReport("r002");
        dto.setTitle("UI Bug");
        dto.setDescription("Wrong alignment on mobile view");
        dto.setAuthorId("u002");
        dto.setAuthorEmail("user2@example.com");
        dto.setAuthorFullName("User Two");
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);

        assertEquals("r002", dto.getIdReport());
        assertEquals("UI Bug", dto.getTitle());
        assertEquals("Wrong alignment on mobile view", dto.getDescription());
        assertEquals("u002", dto.getAuthorId());
        assertEquals("user2@example.com", dto.getAuthorEmail());
        assertEquals("User Two", dto.getAuthorFullName());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    void testEqualityAndHashCode_HappyPath() {
        LocalDateTime time = LocalDateTime.now();
        ReportDto dto1 = new ReportDto("id1", "Title", "Desc", "u1", "email", "Name", time, time);
        ReportDto dto2 = new ReportDto("id1", "Title", "Desc", "u1", "email", "Name", time, time);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testInequality_UnhappyPath() {
        LocalDateTime time = LocalDateTime.now();
        ReportDto dto1 = new ReportDto("id1", "Title", "Desc", "u1", "email", "Name", time, time);
        ReportDto dto2 = new ReportDto("id2", "Other Title", "Other Desc", "u2", "other@email", "Other Name", time, time);

        assertNotEquals(dto1, dto2);
    }

    @Test
    void testNullFields_UnhappyPath() {
        ReportDto dto = new ReportDto();
        assertNull(dto.getIdReport());
        assertNull(dto.getTitle());
        assertNull(dto.getDescription());
        assertNull(dto.getAuthorId());
        assertNull(dto.getAuthorEmail());
        assertNull(dto.getAuthorFullName());
        assertNull(dto.getCreatedAt());
        assertNull(dto.getUpdatedAt());
    }
}