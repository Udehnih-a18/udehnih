package id.ac.ui.cs.advprog.udehnihh.review.repository;

import id.ac.ui.cs.advprog.udehnihh.review.model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ReviewRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    private Review sampleReview1;
    private Review sampleReview2;
    private Review sampleReview3;
    
    @BeforeEach
    void setUp() {
        // Clear any existing data
        reviewRepository.deleteAll();
        entityManager.flush();
        entityManager.clear();
        
        // Create sample reviews
        sampleReview1 = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Excellent course!")
            .build();
        
        sampleReview2 = Review.builder()
            .studentId(2L)
            .courseId(1L)
            .rating(4)
            .comment("Good course")
            .build();
        
        sampleReview3 = Review.builder()
            .studentId(1L)
            .courseId(2L)
            .rating(3)
            .comment("Average course")
            .build();
    }
    
    @Test
    @DisplayName("Should save and find review by ID")
    void shouldSaveAndFindReviewById() {
        // Given & When
        Review savedReview = reviewRepository.save(sampleReview1);
        entityManager.flush();
        
        Optional<Review> foundReview = reviewRepository.findById(savedReview.getId());
        
        // Then
        assertThat(foundReview).isPresent();
        assertThat(foundReview.get().getStudentId()).isEqualTo(1L);
        assertThat(foundReview.get().getCourseId()).isEqualTo(1L);
        assertThat(foundReview.get().getRating()).isEqualTo(5);
        assertThat(foundReview.get().getComment()).isEqualTo("Excellent course!");
        assertThat(foundReview.get().getCreatedAt()).isNotNull();
        assertThat(foundReview.get().getUpdatedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("Should find reviews by course ID ordered by created date desc")
    void shouldFindReviewsByCourseIdOrderedByCreatedAtDesc() {
        // Given
        Review review1 = reviewRepository.save(sampleReview1);
        entityManager.flush();
        entityManager.clear();
        
        // Add some delay to ensure different timestamps
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Review review2 = reviewRepository.save(sampleReview2);
        entityManager.flush();
        entityManager.clear();
        
        // When
        List<Review> reviews = reviewRepository.findByCourseIdOrderByCreatedAtDesc(1L);
        
        // Then
        assertThat(reviews).hasSize(2);
        // Most recent review should be first
        assertThat(reviews.get(0).getStudentId()).isEqualTo(2L);
        assertThat(reviews.get(0).getRating()).isEqualTo(4);
        assertThat(reviews.get(1).getStudentId()).isEqualTo(1L);
        assertThat(reviews.get(1).getRating()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("Should return empty list when no reviews found for course")
    void shouldReturnEmptyListWhenNoReviewsFoundForCourse() {
        // Given
        reviewRepository.save(sampleReview1);
        entityManager.flush();
        
        // When
        List<Review> reviews = reviewRepository.findByCourseIdOrderByCreatedAtDesc(999L);
        
        // Then
        assertThat(reviews).isEmpty();
    }
    
    @Test
    @DisplayName("Should check if review exists by student and course ID")
    void shouldCheckIfReviewExistsByStudentAndCourseId() {
        // Given
        reviewRepository.save(sampleReview1);
        entityManager.flush();
        
        // When & Then
        assertThat(reviewRepository.existsByStudentIdAndCourseId(1L, 1L)).isTrue();
        assertThat(reviewRepository.existsByStudentIdAndCourseId(1L, 2L)).isFalse();
        assertThat(reviewRepository.existsByStudentIdAndCourseId(2L, 1L)).isFalse();
        assertThat(reviewRepository.existsByStudentIdAndCourseId(999L, 999L)).isFalse();
    }
    
    @Test
    @DisplayName("Should enforce unique constraint on student and course")
    void shouldEnforceUniqueConstraintOnStudentAndCourse() {
        // Given
        reviewRepository.save(sampleReview1);
        entityManager.flush();
        
        // When & Then - Try to save duplicate review for same student and course
        Review duplicateReview = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(3)
            .comment("Duplicate review")
            .build();
        
        assertThatThrownBy(() -> {
            reviewRepository.save(duplicateReview);
            entityManager.flush();
        }).hasMessageContaining("constraint");
    }
    
    @Test
    @DisplayName("Should allow same student to review different courses")
    void shouldAllowSameStudentToReviewDifferentCourses() {
        // Given & When
        Review review1 = reviewRepository.save(sampleReview1); // Student 1, Course 1
        Review review3 = reviewRepository.save(sampleReview3); // Student 1, Course 2
        entityManager.flush();
        
        // Then
        assertThat(review1.getId()).isNotNull();
        assertThat(review3.getId()).isNotNull();
        assertThat(review1.getId()).isNotEqualTo(review3.getId());
    }
    
    @Test
    @DisplayName("Should allow different students to review same course")
    void shouldAllowDifferentStudentsToReviewSameCourse() {
        // Given & When
        Review review1 = reviewRepository.save(sampleReview1); // Student 1, Course 1
        Review review2 = reviewRepository.save(sampleReview2); // Student 2, Course 1
        entityManager.flush();
        
        // Then
        assertThat(review1.getId()).isNotNull();
        assertThat(review2.getId()).isNotNull();
        assertThat(review1.getId()).isNotEqualTo(review2.getId());
    }
    
    @Test
    @DisplayName("Should update review successfully")
    void shouldUpdateReviewSuccessfully() {
        // Given
        Review savedReview = reviewRepository.save(sampleReview1);
        entityManager.flush();
        entityManager.clear();
        
        // When
        Review reviewToUpdate = reviewRepository.findById(savedReview.getId()).get();
        reviewToUpdate.setRating(4);
        reviewToUpdate.setComment("Updated comment");
        Review updatedReview = reviewRepository.save(reviewToUpdate);
        entityManager.flush();
        
        // Then
        assertThat(updatedReview.getRating()).isEqualTo(4);
        assertThat(updatedReview.getComment()).isEqualTo("Updated comment");
        assertThat(updatedReview.getUpdatedAt()).isAfter(updatedReview.getCreatedAt());
    }
    
    @Test
    @DisplayName("Should delete review successfully")
    void shouldDeleteReviewSuccessfully() {
        // Given
        Review savedReview = reviewRepository.save(sampleReview1);
        entityManager.flush();
        Long reviewId = savedReview.getId();
        
        // When
        reviewRepository.delete(savedReview);
        entityManager.flush();
        
        // Then
        Optional<Review> deletedReview = reviewRepository.findById(reviewId);
        assertThat(deletedReview).isEmpty();
    }
    
    @Test
    @DisplayName("Should validate rating constraints at database level")
    void shouldValidateRatingConstraintsAtDatabaseLevel() {
        // Test valid ratings
        for (int rating = 1; rating <= 5; rating++) {
            Review review = Review.builder()
                .studentId(1L)
                .courseId((long) rating) // Different course for each rating
                .rating(rating)
                .comment("Rating " + rating)
                .build();
            
            Review savedReview = reviewRepository.save(review);
            entityManager.flush();
            
            assertThat(savedReview.getRating()).isEqualTo(rating);
        }
    }
    
    @Test
    @DisplayName("Should handle null comment")
    void shouldHandleNullComment() {
        // Given
        Review reviewWithNullComment = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment(null)
            .build();
        
        // When
        Review savedReview = reviewRepository.save(reviewWithNullComment);
        entityManager.flush();
        
        // Then
        assertThat(savedReview.getComment()).isNull();
        assertThat(savedReview.getRating()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("Should handle empty comment")
    void shouldHandleEmptyComment() {
        // Given
        Review reviewWithEmptyComment = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("")
            .build();
        
        // When
        Review savedReview = reviewRepository.save(reviewWithEmptyComment);
        entityManager.flush();
        
        // Then
        assertThat(savedReview.getComment()).isEmpty();
        assertThat(savedReview.getRating()).isEqualTo(5);
    }
    
    @Test
    @DisplayName("Should handle long comment within limit")
    void shouldHandleLongCommentWithinLimit() {
        // Given - Comment with 1000 characters (at the limit)
        String longComment = "a".repeat(1000);
        Review reviewWithLongComment = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment(longComment)
            .build();
        
        // When
        Review savedReview = reviewRepository.save(reviewWithLongComment);
        entityManager.flush();
        
        // Then
        assertThat(savedReview.getComment()).hasSize(1000);
        assertThat(savedReview.getComment()).isEqualTo(longComment);
    }
    
    @Test
    @DisplayName("Should auto-generate timestamps")
    void shouldAutoGenerateTimestamps() {
        // Given
        LocalDateTime beforeSave = LocalDateTime.now();
        
        // When
        Review savedReview = reviewRepository.save(sampleReview1);
        entityManager.flush();
        
        LocalDateTime afterSave = LocalDateTime.now();
        
        // Then
        assertThat(savedReview.getCreatedAt()).isNotNull();
        assertThat(savedReview.getUpdatedAt()).isNotNull();
        assertThat(savedReview.getCreatedAt()).isBetween(beforeSave, afterSave);
        assertThat(savedReview.getUpdatedAt()).isBetween(beforeSave, afterSave);
        assertThat(savedReview.getCreatedAt()).isEqualTo(savedReview.getUpdatedAt());
    }
    
    @Test
    @DisplayName("Should update timestamp on modification")
    void shouldUpdateTimestampOnModification() {
        // Given
        Review savedReview = reviewRepository.save(sampleReview1);
        entityManager.flush();
        LocalDateTime originalCreatedAt = savedReview.getCreatedAt();
        LocalDateTime originalUpdatedAt = savedReview.getUpdatedAt();
        
        // Add small delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // When
        savedReview.setComment("Updated comment");
        Review updatedReview = reviewRepository.save(savedReview);
        entityManager.flush();
        
        // Then
        assertThat(updatedReview.getCreatedAt()).isEqualTo(originalCreatedAt);
        assertThat(updatedReview.getUpdatedAt()).isAfter(originalUpdatedAt);
    }
    
    @Test
    @DisplayName("Should count all reviews correctly")
    void shouldCountAllReviewsCorrectly() {
        // Given
        reviewRepository.save(sampleReview1);
        reviewRepository.save(sampleReview2);
        reviewRepository.save(sampleReview3);
        entityManager.flush();
        
        // When
        long count = reviewRepository.count();
        
        // Then
        assertThat(count).isEqualTo(3);
    }
    
    @Test
    @DisplayName("Should find all reviews")
    void shouldFindAllReviews() {
        // Given
        reviewRepository.save(sampleReview1);
        reviewRepository.save(sampleReview2);
        reviewRepository.save(sampleReview3);
        entityManager.flush();
        
        // When
        List<Review> allReviews = reviewRepository.findAll();
        
        // Then
        assertThat(allReviews).hasSize(3);
        assertThat(allReviews).extracting("rating").containsExactlyInAnyOrder(5, 4, 3);
    }
}