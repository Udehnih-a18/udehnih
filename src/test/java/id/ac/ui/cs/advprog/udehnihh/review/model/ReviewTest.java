package id.ac.ui.cs.advprog.udehnihh.review.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    @Test
    void createReview_AllArgsConstructor() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        Review review = new Review(1L, studentId, courseId, 5, "Great course!", now, now);

        assertEquals(1L, review.getId());
        assertEquals(studentId, review.getStudentId());
        assertEquals(courseId, review.getCourseId());
        assertEquals(5, review.getRating());
        assertEquals("Great course!", review.getComment());
        assertEquals(now, review.getCreatedAt());
        assertEquals(now, review.getUpdatedAt());
    }

    @Test
    void createReview_Builder() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        Review review = Review.builder()
                .id(1L)
                .studentId(studentId)
                .courseId(courseId)
                .rating(4)
                .comment("Good course")
                .build();

        assertEquals(1L, review.getId());
        assertEquals(studentId, review.getStudentId());
        assertEquals(courseId, review.getCourseId());
        assertEquals(4, review.getRating());
        assertEquals("Good course", review.getComment());
    }

    @Test
    void createReview_NoArgsConstructor() {
        Review review = new Review();
        assertNotNull(review);
    }

    @Test
    void settersAndGetters_Work() {
        Review review = new Review();
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();

        review.setId(1L);
        review.setStudentId(studentId);
        review.setCourseId(courseId);
        review.setRating(5);
        review.setComment("Excellent!");
        review.setCreatedAt(now);
        review.setUpdatedAt(now);

        assertEquals(1L, review.getId());
        assertEquals(studentId, review.getStudentId());
        assertEquals(courseId, review.getCourseId());
        assertEquals(5, review.getRating());
        assertEquals("Excellent!", review.getComment());
        assertEquals(now, review.getCreatedAt());
        assertEquals(now, review.getUpdatedAt());
    }

    @Test
    void equalsAndHashCode_Work() {
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        Review review1 = Review.builder()
                .id(1L)
                .studentId(studentId)
                .courseId(courseId)
                .rating(5)
                .comment("Great!")
                .build();

        Review review2 = Review.builder()
                .id(1L)
                .studentId(studentId)
                .courseId(courseId)
                .rating(5)
                .comment("Great!")
                .build();

        assertEquals(review1, review2);
        assertEquals(review1.hashCode(), review2.hashCode());
    }

    @Test
    void toString_Works() {
        Review review = Review.builder()
                .id(1L)
                .studentId(UUID.randomUUID())
                .courseId(UUID.randomUUID())
                .rating(5)
                .comment("Great!")
                .build();

        String toString = review.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Review"));
    }
}
