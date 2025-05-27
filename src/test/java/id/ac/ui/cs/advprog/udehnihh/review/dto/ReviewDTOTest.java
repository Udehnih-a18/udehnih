package id.ac.ui.cs.advprog.udehnihh.review.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewDTOTest {

    @Test
    void createReviewDTO_AllArgsConstructor() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        ReviewDTO dto = new ReviewDTO(1L, studentId, "John Doe", now, now, courseId, 5, "Great course!");

        assertEquals(1L, dto.getId());
        assertEquals(studentId, dto.getStudentId());
        assertEquals("John Doe", dto.getReviewerName());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals(courseId, dto.getCourseId());
        assertEquals(5, dto.getRating());
        assertEquals("Great course!", dto.getComment());
    }

    @Test
    void createReviewDTO_Builder() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        ReviewDTO dto = ReviewDTO.builder()
                .id(1L)
                .studentId(studentId)
                .reviewerName("Jane Doe")
                .courseId(courseId)
                .rating(4)
                .comment("Good course")
                .build();

        assertEquals(1L, dto.getId());
        assertEquals(studentId, dto.getStudentId());
        assertEquals("Jane Doe", dto.getReviewerName());
        assertEquals(courseId, dto.getCourseId());
        assertEquals(4, dto.getRating());
        assertEquals("Good course", dto.getComment());
    }

    @Test
    void createReviewDTO_NoArgsConstructor() {
        ReviewDTO dto = new ReviewDTO();
        assertNotNull(dto);
    }

    @Test
    void settersAndGetters_Work() {
        ReviewDTO dto = new ReviewDTO();
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        dto.setId(1L);
        dto.setStudentId(studentId);
        dto.setReviewerName("Test User");
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);
        dto.setCourseId(courseId);
        dto.setRating(5);
        dto.setComment("Excellent!");

        assertEquals(1L, dto.getId());
        assertEquals(studentId, dto.getStudentId());
        assertEquals("Test User", dto.getReviewerName());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
        assertEquals(courseId, dto.getCourseId());
        assertEquals(5, dto.getRating());
        assertEquals("Excellent!", dto.getComment());
    }

    @Test
    void equalsAndHashCode_Work() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        ReviewDTO dto1 = ReviewDTO.builder()
                .id(1L)
                .studentId(studentId)
                .courseId(courseId)
                .rating(5)
                .comment("Great!")
                .build();

        ReviewDTO dto2 = ReviewDTO.builder()
                .id(1L)
                .studentId(studentId)
                .courseId(courseId)
                .rating(5)
                .comment("Great!")
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void toString_Works() {
        ReviewDTO dto = ReviewDTO.builder()
                .id(1L)
                .courseId(UUID.randomUUID())
                .rating(5)
                .comment("Great!")
                .build();

        String toString = dto.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("ReviewDTO"));
    }

    @Test
    void validationGroups_Exist() {
        assertNotNull(ReviewDTO.Create.class);
        assertNotNull(ReviewDTO.Update.class);
    }
}
