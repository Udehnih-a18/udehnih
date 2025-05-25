package id.ac.ui.cs.advprog.udehnihh.review.model;

import id.ac.ui.cs.advprog.udehnihh.review.model.Review;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class ReviewTest {
    
    private Validator validator;
    
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    @DisplayName("Should create valid review with all fields")
    void shouldCreateValidReview() {
        // Given & When
        Review review = Review.builder()
            .id(1L)
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Excellent course!")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        
        // Then
        assertThat(review.getId()).isEqualTo(1L);
        assertThat(review.getStudentId()).isEqualTo(1L);
        assertThat(review.getCourseId()).isEqualTo(1L);
        assertThat(review.getRating()).isEqualTo(5);
        assertThat(review.getComment()).isEqualTo("Excellent course!");
        assertThat(review.getCreatedAt()).isNotNull();
        assertThat(review.getUpdatedAt()).isNotNull();
    }
    
    @Test
    @DisplayName("Should create review without comment")
    void shouldCreateReviewWithoutComment() {
        // Given & When
        Review review = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(4)
            .build();
        
        // Then
        assertThat(review.getComment()).isNull();
        assertThat(review.getRating()).isEqualTo(4);
        
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        assertThat(violations).isEmpty();
    }
    
    @Test
    @DisplayName("Should validate rating constraints")
    void shouldValidateRatingConstraints() {
        // Test minimum valid rating (1)
        Review validReview1 = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(1)
            .build();
        
        Set<ConstraintViolation<Review>> violations1 = validator.validate(validReview1);
        assertThat(violations1).isEmpty();
        
        // Test maximum valid rating (5)
        Review validReview5 = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .build();
        
        Set<ConstraintViolation<Review>> violations5 = validator.validate(validReview5);
        assertThat(violations5).isEmpty();
    }
    
    @Test
    @DisplayName("Should reject rating below minimum (1)")
    void shouldRejectRatingBelowMinimum() {
        // Given
        Review invalidReview = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(0) // Below minimum
            .build();
        
        // When
        Set<ConstraintViolation<Review>> violations = validator.validate(invalidReview);
        
        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .contains("must be greater than or equal to 1");
    }
    
    @Test
    @DisplayName("Should reject rating above maximum (5)")
    void shouldRejectRatingAboveMaximum() {
        // Given
        Review invalidReview = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(6) // Above maximum
            .build();
        
        // When
        Set<ConstraintViolation<Review>> violations = validator.validate(invalidReview);
        
        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .contains("must be less than or equal to 5");
    }
    
    @Test
    @DisplayName("Should reject null rating")
    void shouldRejectNullRating() {
        // Given
        Review invalidReview = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(null) // Null rating
            .build();
        
        // When
        Set<ConstraintViolation<Review>> violations = validator.validate(invalidReview);
        
        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .contains("must not be null");
    }
    
    @Test
    @DisplayName("Should reject null studentId")
    void shouldRejectNullStudentId() {
        // Given
        Review invalidReview = Review.builder()
            .studentId(null) // Null studentId
            .courseId(1L)
            .rating(5)
            .build();
        
        // When
        Set<ConstraintViolation<Review>> violations = validator.validate(invalidReview);
        
        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .contains("must not be null");
    }
    
    @Test
    @DisplayName("Should reject null courseId")
    void shouldRejectNullCourseId() {
        // Given
        Review invalidReview = Review.builder()
            .studentId(1L)
            .courseId(null) // Null courseId
            .rating(5)
            .build();
        
        // When
        Set<ConstraintViolation<Review>> violations = validator.validate(invalidReview);
        
        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .contains("must not be null");
    }
    
    @Test
    @DisplayName("Should handle comment length constraint")
    void shouldHandleCommentLengthConstraint() {
        // Test comment at maximum length (1000 characters)
        String maxLengthComment = "a".repeat(1000);
        Review validReview = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment(maxLengthComment)
            .build();
        
        Set<ConstraintViolation<Review>> violations = validator.validate(validReview);
        assertThat(violations).isEmpty();
        
        // Test comment exceeding maximum length
        String tooLongComment = "a".repeat(1001);
        Review invalidReview = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment(tooLongComment)
            .build();
        
        Set<ConstraintViolation<Review>> violationsLong = validator.validate(invalidReview);
        assertThat(violationsLong).hasSize(1);
        assertThat(violationsLong.iterator().next().getMessage())
            .contains("length must be between 0 and 1000");
    }
    
    @Test
    @DisplayName("Should support builder pattern correctly")
    void shouldSupportBuilderPattern() {
        // Given & When
        Review review = Review.builder()
            .id(1L)
            .studentId(2L)
            .courseId(3L)
            .rating(4)
            .comment("Good course")
            .build();
        
        // Then
        assertThat(review)
            .extracting("id", "studentId", "courseId", "rating", "comment")
            .containsExactly(1L, 2L, 3L, 4, "Good course");
    }
    
    @Test
    @DisplayName("Should support equals and hashCode")
    void shouldSupportEqualsAndHashCode() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        
        Review review1 = Review.builder()
            .id(1L)
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Test")
            .createdAt(now)
            .updatedAt(now)
            .build();
        
        Review review2 = Review.builder()
            .id(1L)
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Test")
            .createdAt(now)
            .updatedAt(now)
            .build();
        
        Review review3 = Review.builder()
            .id(2L) // Different ID
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Test")
            .createdAt(now)
            .updatedAt(now)
            .build();
        
        // Then
        assertThat(review1).isEqualTo(review2);
        assertThat(review1).isNotEqualTo(review3);
        assertThat(review1.hashCode()).isEqualTo(review2.hashCode());
        assertThat(review1.hashCode()).isNotEqualTo(review3.hashCode());
    }
    
    @Test
    @DisplayName("Should support toString method")
    void shouldSupportToString() {
        // Given
        Review review = Review.builder()
            .id(1L)
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Test comment")
            .build();
        
        // When
        String toString = review.toString();
        
        // Then
        assertThat(toString)
            .contains("Review")
            .contains("id=1")
            .contains("studentId=1")
            .contains("courseId=1")
            .contains("rating=5")
            .contains("comment=Test comment");
    }
    
    @Test
    @DisplayName("Should handle empty comment")
    void shouldHandleEmptyComment() {
        // Given
        Review review = Review.builder()
            .studentId(1L)
            .courseId(1L)
            .rating(3)
            .comment("")
            .build();
        
        // When
        Set<ConstraintViolation<Review>> violations = validator.validate(review);
        
        // Then
        assertThat(violations).isEmpty();
        assertThat(review.getComment()).isEmpty();
    }
    
    @Test
    @DisplayName("Should validate all fields are not null except comment")
    void shouldValidateRequiredFields() {
        // Given - Review with all required fields null
        Review invalidReview = Review.builder().build();
        
        // When
        Set<ConstraintViolation<Review>> violations = validator.validate(invalidReview);
        
        // Then - Should have violations for studentId, courseId, and rating
        assertThat(violations).hasSize(3);
        
        Set<String> propertyPaths = violations.stream()
            .map(v -> v.getPropertyPath().toString())
            .collect(java.util.stream.Collectors.toSet());
        
        assertThat(propertyPaths).containsExactlyInAnyOrder("studentId", "courseId", "rating");
    }
}