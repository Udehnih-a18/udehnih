package id.ac.ui.cs.advprog.udehnihh.report.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportDTOTest {

    @Test
    void testReportDTOGettersAndSetters() {
        ReportDTO reportDTO = new ReportDTO();
        String title = "Test Title";
        String description = "Test Description";

        reportDTO.setTitle(title);
        reportDTO.setDescription(description);

        assertEquals(title, reportDTO.getTitle());
        assertEquals(description, reportDTO.getDescription());
    }

    @Test
    void testReportDTOEqualsAndHashCode() {
        ReportDTO reportDTO1 = new ReportDTO();
        reportDTO1.setTitle("Same Title");
        reportDTO1.setDescription("Same Description");

        ReportDTO reportDTO2 = new ReportDTO();
        reportDTO2.setTitle("Same Title");
        reportDTO2.setDescription("Same Description");

        ReportDTO reportDTO3 = new ReportDTO();
        reportDTO3.setTitle("Different Title");
        reportDTO3.setDescription("Different Description");

        assertEquals(reportDTO1, reportDTO2);
        assertNotEquals(reportDTO1, reportDTO3);
        assertEquals(reportDTO1.hashCode(), reportDTO2.hashCode());
        assertNotEquals(reportDTO1.hashCode(), reportDTO3.hashCode());
    }
}