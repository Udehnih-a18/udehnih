package id.ac.ui.cs.advprog.udehnihh.review.service;

import id.ac.ui.cs.advprog.udehnihh.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.udehnihh.review.model.Review;
import id.ac.ui.cs.advprog.udehnihh.review.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    
    @Mock
    private ReviewRepository reviewRepository;
    
    @InjectMocks
    private ReviewService reviewService;
    
    @Test
    void shouldCreateReview() {
        // Given
        Long studentId = 1L;
        ReviewDTO dto = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment("Great!")
            .build();
        
        Review savedReview = Review.builder()
            .id(1L)
            .studentId(studentId)
            .courseId(1L)
            .rating(5)
            .comment("Great!")
            .build();
        
        when(reviewRepository.existsByStudentIdAndCourseId(studentId, 1L)).thenReturn(false);
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);
        
        // When
        ReviewDTO result = reviewService.createReview(studentId, dto);
        
        // Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getRating()).isEqualTo(5);
        verify(reviewRepository).save(any(Review.class));
    }
    
    @Test
    void shouldThrowExceptionWhenReviewAlreadyExists() {
        // Given
        Long studentId = 1L;
        ReviewDTO dto = ReviewDTO.builder().courseId(1L).rating(5).build();
        
        when(reviewRepository.existsByStudentIdAndCourseId(studentId, 1L)).thenReturn(true);
        
        // When & Then
        assertThatThrownBy(() -> reviewService.createReview(studentId, dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Review already exists");
    }
    
    @Test
    void shouldGetCourseReviews() {
        // Given
        Long courseId = 1L;
        List<Review> reviews = Arrays.asList(
            Review.builder().id(1L).rating(5).build(),
            Review.builder().id(2L).rating(4).build()
        );
        
        when(reviewRepository.findByCourseIdOrderByCreatedAtDesc(courseId)).thenReturn(reviews);
        
        // When
        List<ReviewDTO> result = reviewService.getCourseReviews(courseId);
        
        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getRating()).isEqualTo(5);
    }
    
    @Test
    void shouldUpdateReview() {
        // Given
        Long reviewId = 1L;
        Long studentId = 1L;
        ReviewDTO dto = ReviewDTO.builder().rating(4).comment("Updated").build();
        
        Review existingReview = Review.builder()
            .id(reviewId)
            .studentId(studentId)
            .rating(5)
            .comment("Original")
            .build();
        
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(existingReview)).thenReturn(existingReview);
        
        // When
        ReviewDTO result = reviewService.updateReview(reviewId, studentId, dto);
        
        // Then
        assertThat(result.getRating()).isEqualTo(4);
        assertThat(result.getComment()).isEqualTo("Updated");
    }
    
    @Test
    void shouldThrowExceptionWhenUnauthorizedUpdate() {
        // Given
        Long reviewId = 1L;
        Long studentId = 1L;
        Long otherStudentId = 2L;
        ReviewDTO dto = ReviewDTO.builder().rating(4).build();
        
        Review existingReview = Review.builder()
            .id(reviewId)
            .studentId(otherStudentId)
            .build();
        
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        
        // When & Then
        assertThatThrownBy(() -> reviewService.updateReview(reviewId, studentId, dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Unauthorized");
    }
    
    @Test 
    void shouldDeleteReview() {
        // Given
        Long reviewId = 1L;
        Long studentId = 1L;
        
        Review existingReview = Review.builder()
            .id(reviewId)
            .studentId(studentId)
            .build();
        
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
        
        // When
        reviewService.deleteReview(reviewId, studentId);
        
        // Then
        verify(reviewRepository).delete(existingReview);
    }
}