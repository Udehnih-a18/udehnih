package id.ac.ui.cs.advprog.udehnihh.review.repository;

import id.ac.ui.cs.advprog.udehnihh.review.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private UUID studentId1;
    private UUID studentId2;
    private UUID courseId1;
    private UUID courseId2;

    @BeforeEach
    void setUp() {
        studentId1 = UUID.randomUUID();
        studentId2 = UUID.randomUUID();
        courseId1 = UUID.randomUUID();
        courseId2 = UUID.randomUUID();

        // Create test data
        Review review1 = Review.builder()
                .studentId(studentId1)
                .courseId(courseId1)
                .rating(5)
                .comment("Excellent course!")
                .build();

        Review review2 = Review.builder()
                .studentId(studentId2)
                .courseId(courseId1)
                .rating(4)
                .comment("Good course!")
                .build();

        Review review3 = Review.builder()
                .studentId(studentId1)
                .courseId(courseId2)
                .rating(3)
                .comment("Average course")
                .build();

        reviewRepository.saveAll(List.of(review1, review2, review3));
    }

    @Test
    void findByCourseIdOrderByCreatedAtDesc_Success() {
        List<Review> reviews = reviewRepository.findByCourseIdOrderByCreatedAtDesc(courseId1);

        assertEquals(2, reviews.size());
        assertTrue(reviews.stream().allMatch(r -> r.getCourseId().equals(courseId1)));
    }

    @Test
    void existsByStudentIdAndCourseId_True() {
        boolean exists = reviewRepository.existsByStudentIdAndCourseId(studentId1, courseId1);
        assertTrue(exists);
    }

    @Test
    void existsByStudentIdAndCourseId_False() {
        UUID nonExistentCourseId = UUID.randomUUID();
        boolean exists = reviewRepository.existsByStudentIdAndCourseId(studentId1, nonExistentCourseId);
        assertFalse(exists);
    }

    @Test
    void findByStudentIdAndCourseId_Found() {
        Optional<Review> review = reviewRepository.findByStudentIdAndCourseId(studentId1, courseId1);
        
        assertTrue(review.isPresent());
        assertEquals(studentId1, review.get().getStudentId());
        assertEquals(courseId1, review.get().getCourseId());
    }

    @Test
    void findByStudentIdAndCourseId_NotFound() {
        UUID nonExistentCourseId = UUID.randomUUID();
        Optional<Review> review = reviewRepository.findByStudentIdAndCourseId(studentId1, nonExistentCourseId);
        
        assertTrue(review.isEmpty());
    }

    @Test
    void findByStudentIdOrderByCreatedAtDesc_Success() {
        List<Review> reviews = reviewRepository.findByStudentIdOrderByCreatedAtDesc(studentId1);

        assertEquals(2, reviews.size());
        assertTrue(reviews.stream().allMatch(r -> r.getStudentId().equals(studentId1)));
    }

    @Test
    void findAverageRatingByCourseId_Success() {
        Double averageRating = reviewRepository.findAverageRatingByCourseId(courseId1);

        assertNotNull(averageRating);
        assertEquals(4.5, averageRating); // (5 + 4) / 2
    }

    @Test
    void findAverageRatingByCourseId_NoReviews() {
        UUID nonExistentCourseId = UUID.randomUUID();
        Double averageRating = reviewRepository.findAverageRatingByCourseId(nonExistentCourseId);

        assertNull(averageRating);
    }

    @Test
    void countByCourseId_Success() {
        Long count = reviewRepository.countByCourseId(courseId1);

        assertEquals(2L, count);
    }

    @Test
    void countByCourseId_NoReviews() {
        UUID nonExistentCourseId = UUID.randomUUID();
        Long count = reviewRepository.countByCourseId(nonExistentCourseId);

        assertEquals(0L, count);
    }

    @Test
    void uniqueConstraint_PreventsDuplicateReviews() {
        Review duplicateReview = Review.builder()
                .studentId(studentId1)
                .courseId(courseId1) // Same student and course as existing review
                .rating(2)
                .comment("Duplicate review")
                .build();

        assertThrows(Exception.class, () -> {
            reviewRepository.saveAndFlush(duplicateReview);
        });
    }
}
