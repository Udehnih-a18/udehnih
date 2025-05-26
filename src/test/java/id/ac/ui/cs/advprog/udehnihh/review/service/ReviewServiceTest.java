package id.ac.ui.cs.advprog.udehnihh.review.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.udehnihh.review.model.Review;
import id.ac.ui.cs.advprog.udehnihh.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReviewService reviewService;

    private UUID studentId;
    private UUID courseId;
    private Review review;
    private ReviewDTO reviewDTO;
    private User user;

    @BeforeEach
    void setUp() {
        studentId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        
        review = Review.builder()
                .id(1L)
                .studentId(studentId)
                .courseId(courseId)
                .rating(5)
                .comment("Great course!")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        reviewDTO = ReviewDTO.builder()
                .courseId(courseId)
                .rating(5)
                .comment("Great course!")
                .build();

        user = User.builder()
                .id(studentId)
                .email("test@example.com")
                .fullName("Test User")
                .build();
    }

    @Test
    void createReview_Success() {
        when(reviewRepository.existsByStudentIdAndCourseId(studentId, courseId)).thenReturn(false);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewDTO result = reviewService.createReview(studentId, reviewDTO);

        assertNotNull(result);
        assertEquals(studentId, result.getStudentId());
        assertEquals(courseId, result.getCourseId());
        assertEquals(5, result.getRating());
        assertEquals("Great course!", result.getComment());
        
        verify(reviewRepository).existsByStudentIdAndCourseId(studentId, courseId);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void createReview_ReviewAlreadyExists_ThrowsException() {
        when(reviewRepository.existsByStudentIdAndCourseId(studentId, courseId)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewService.createReview(studentId, reviewDTO));

        assertEquals("Review already exists", exception.getMessage());
        verify(reviewRepository).existsByStudentIdAndCourseId(studentId, courseId);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void getCourseReviews_Success() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findByCourseIdOrderByCreatedAtDesc(courseId)).thenReturn(reviews);
        when(userRepository.findById(studentId)).thenReturn(Optional.of(user));

        List<ReviewDTO> result = reviewService.getCourseReviews(courseId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test User", result.get(0).getReviewerName());
        
        verify(reviewRepository).findByCourseIdOrderByCreatedAtDesc(courseId);
        verify(userRepository).findById(studentId);
    }

    @Test
    void getCourseReviews_UserNotFound_AnonymousReviewer() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findByCourseIdOrderByCreatedAtDesc(courseId)).thenReturn(reviews);
        when(userRepository.findById(studentId)).thenReturn(Optional.empty());

        List<ReviewDTO> result = reviewService.getCourseReviews(courseId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Anonymous", result.get(0).getReviewerName());
        
        verify(reviewRepository).findByCourseIdOrderByCreatedAtDesc(courseId);
        verify(userRepository).findById(studentId);
    }

    @Test
    void getStudentReviews_Success() {
        List<Review> reviews = Arrays.asList(review);
        when(reviewRepository.findByStudentIdOrderByCreatedAtDesc(studentId)).thenReturn(reviews);

        List<ReviewDTO> result = reviewService.getStudentReviews(studentId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(studentId, result.get(0).getStudentId());
        
        verify(reviewRepository).findByStudentIdOrderByCreatedAtDesc(studentId);
    }

    @Test
    void getStudentReviewForCourse_Found() {
        when(reviewRepository.findByStudentIdAndCourseId(studentId, courseId)).thenReturn(Optional.of(review));

        Optional<ReviewDTO> result = reviewService.getStudentReviewForCourse(studentId, courseId);

        assertTrue(result.isPresent());
        assertEquals(studentId, result.get().getStudentId());
        assertEquals(courseId, result.get().getCourseId());
        
        verify(reviewRepository).findByStudentIdAndCourseId(studentId, courseId);
    }

    @Test
    void getStudentReviewForCourse_NotFound() {
        when(reviewRepository.findByStudentIdAndCourseId(studentId, courseId)).thenReturn(Optional.empty());

        Optional<ReviewDTO> result = reviewService.getStudentReviewForCourse(studentId, courseId);

        assertTrue(result.isEmpty());
        
        verify(reviewRepository).findByStudentIdAndCourseId(studentId, courseId);
    }

    @Test
    void updateReview_Success() {
        ReviewDTO updateDTO = ReviewDTO.builder()
                .rating(4)
                .comment("Updated comment")
                .build();

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        ReviewDTO result = reviewService.updateReview(1L, studentId, updateDTO);

        assertNotNull(result);
        verify(reviewRepository).findById(1L);
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void updateReview_ReviewNotFound_ThrowsException() {
        ReviewDTO updateDTO = ReviewDTO.builder()
                .rating(4)
                .comment("Updated comment")
                .build();

        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewService.updateReview(1L, studentId, updateDTO));

        assertEquals("Review not found", exception.getMessage());
        verify(reviewRepository).findById(1L);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void updateReview_Unauthorized_ThrowsException() {
        UUID differentStudentId = UUID.randomUUID();
        ReviewDTO updateDTO = ReviewDTO.builder()
                .rating(4)
                .comment("Updated comment")
                .build();

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewService.updateReview(1L, differentStudentId, updateDTO));

        assertEquals("Unauthorized", exception.getMessage());
        verify(reviewRepository).findById(1L);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void deleteReview_Success() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        assertDoesNotThrow(() -> reviewService.deleteReview(1L, studentId));

        verify(reviewRepository).findById(1L);
        verify(reviewRepository).delete(review);
    }

    @Test
    void deleteReview_ReviewNotFound_ThrowsException() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewService.deleteReview(1L, studentId));

        assertEquals("Review not found", exception.getMessage());
        verify(reviewRepository).findById(1L);
        verify(reviewRepository, never()).delete(any(Review.class));
    }

    @Test
    void deleteReview_Unauthorized_ThrowsException() {
        UUID differentStudentId = UUID.randomUUID();
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reviewService.deleteReview(1L, differentStudentId));

        assertEquals("Unauthorized", exception.getMessage());
        verify(reviewRepository).findById(1L);
        verify(reviewRepository, never()).delete(any(Review.class));
    }

    @Test
    void getCourseRatingStats_WithData() {
        when(reviewRepository.findAverageRatingByCourseId(courseId)).thenReturn(4.5);
        when(reviewRepository.countByCourseId(courseId)).thenReturn(10L);

        ReviewService.CourseRatingStats result = reviewService.getCourseRatingStats(courseId);

        assertNotNull(result);
        assertEquals(4.5, result.getAverageRating());
        assertEquals(10L, result.getTotalReviews());
        
        verify(reviewRepository).findAverageRatingByCourseId(courseId);
        verify(reviewRepository).countByCourseId(courseId);
    }

    @Test
    void getCourseRatingStats_NoData() {
        when(reviewRepository.findAverageRatingByCourseId(courseId)).thenReturn(null);
        when(reviewRepository.countByCourseId(courseId)).thenReturn(null);

        ReviewService.CourseRatingStats result = reviewService.getCourseRatingStats(courseId);

        assertNotNull(result);
        assertEquals(0.0, result.getAverageRating());
        assertEquals(0L, result.getTotalReviews());
        
        verify(reviewRepository).findAverageRatingByCourseId(courseId);
        verify(reviewRepository).countByCourseId(courseId);
    }

    @Test
    void courseRatingStats_GettersWork() {
        ReviewService.CourseRatingStats stats = new ReviewService.CourseRatingStats(4.2, 15L);
        
        assertEquals(4.2, stats.getAverageRating());
        assertEquals(15L, stats.getTotalReviews());
    }
}
