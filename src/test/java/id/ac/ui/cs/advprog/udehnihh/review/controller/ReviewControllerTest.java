package id.ac.ui.cs.advprog.udehnihh.review.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.udehnihh.review.service.ReviewService;
import id.ac.ui.cs.advprog.udehnihh.review.service.ReviewService.CourseRatingStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    private ReviewService reviewService;
    private ReviewController reviewController;
    private Authentication authentication;
    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        reviewService = mock(ReviewService.class);
        reviewController = new ReviewController(reviewService);
        authentication = mock(Authentication.class);
        user = mock(User.class);
        userId = UUID.randomUUID();

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
    }

    @Test
    void testCreateReview() {
        ReviewDTO dto = new ReviewDTO();
        ReviewDTO result = new ReviewDTO();

        when(reviewService.createReview(eq(userId), eq(dto))).thenReturn(result);

        ReviewDTO response = reviewController.createReview(authentication, dto);
        assertSame(result, response);
    }

    @Test
    void testGetCourseReviews() {
        UUID courseId = UUID.randomUUID();
        List<ReviewDTO> expected = List.of(new ReviewDTO());

        when(reviewService.getCourseReviews(courseId)).thenReturn(expected);

        List<ReviewDTO> result = reviewController.getCourseReviews(courseId);
        assertEquals(expected, result);
    }

    @Test
    void testGetCourseRatingStats() {
        UUID courseId = UUID.randomUUID();
        CourseRatingStats stats = new CourseRatingStats(4.5, 10L);

        when(reviewService.getCourseRatingStats(courseId)).thenReturn(stats);

        CourseRatingStats result = reviewController.getCourseRatingStats(courseId);
        assertEquals(stats, result);
    }

    @Test
    void testGetMyReviews() {
        List<ReviewDTO> reviews = List.of(new ReviewDTO());

        when(reviewService.getStudentReviews(userId)).thenReturn(reviews);

        List<ReviewDTO> result = reviewController.getMyReviews(authentication);
        assertEquals(reviews, result);
    }

    @Test
    void testGetMyReviewForCourseFound() {
        UUID courseId = UUID.randomUUID();
        ReviewDTO review = new ReviewDTO();

        when(reviewService.getStudentReviewForCourse(userId, courseId)).thenReturn(Optional.of(review));

        ResponseEntity<ReviewDTO> result = reviewController.getMyReviewForCourse(courseId, authentication);
        assertEquals(ResponseEntity.ok(review), result);
    }

    @Test
    void testGetMyReviewForCourseNotFound() {
        UUID courseId = UUID.randomUUID();

        when(reviewService.getStudentReviewForCourse(userId, courseId)).thenReturn(Optional.empty());

        ResponseEntity<ReviewDTO> result = reviewController.getMyReviewForCourse(courseId, authentication);
        assertEquals(ResponseEntity.notFound().build(), result);
    }

    @Test
    void testUpdateReview() {
        ReviewDTO dto = new ReviewDTO();
        ReviewDTO updated = new ReviewDTO();
        Long reviewId = 1L;

        when(reviewService.updateReview(reviewId, userId, dto)).thenReturn(updated);

        ReviewDTO result = reviewController.updateReview(reviewId, authentication, dto);
        assertSame(updated, result);
    }

    @Test
    void testDeleteReview() {
        Long reviewId = 1L;

        reviewController.deleteReview(reviewId, authentication);

        verify(reviewService).deleteReview(reviewId, userId);
    }

    @Test
    void testGetUserIdFromAuthWithStringPrincipal() {
        UUID id = UUID.randomUUID();
        Authentication authWithString = mock(Authentication.class);
        when(authWithString.getPrincipal()).thenReturn(id.toString());

        // Test through getMyReviews which uses getUserIdFromAuth internally
        List<ReviewDTO> mockReviews = List.of(new ReviewDTO());
        when(reviewService.getStudentReviews(id)).thenReturn(mockReviews);

        List<ReviewDTO> result = reviewController.getMyReviews(authWithString);
        assertEquals(mockReviews, result);
        verify(reviewService).getStudentReviews(id);
    }

    @Test
    void testGetUserIdFromAuthWithInvalidPrincipalThrowsException() {
        Authentication invalidAuth = mock(Authentication.class);
        when(invalidAuth.getPrincipal()).thenReturn(12345); // not String or User

        // Test through a public method that uses getUserIdFromAuth internally
        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                reviewController.getMyReviews(invalidAuth));
        assertEquals("Unable to extract user ID from authentication", ex.getMessage());
    }

    @Test
    void testHandleIllegalArgumentException() {
        String message = "Invalid argument";
        IllegalArgumentException ex = new IllegalArgumentException(message);

        ReviewController.ErrorResponse response = reviewController.handleIllegalArgumentException(ex);
        assertEquals(message, response.getMessage());
    }

    @Test
    void testHandleGenericException() {
        String msg = "Something went wrong";
        Exception ex = new Exception(msg);

        ReviewController.ErrorResponse response = reviewController.handleGenericException(ex);
        assertEquals("Internal server error: " + msg, response.getMessage());
    }

    @Test
    void testCreateReviewWithException() {
        ReviewDTO dto = new ReviewDTO();
        when(reviewService.createReview(eq(userId), eq(dto)))
                .thenThrow(new IllegalArgumentException("Review already exists"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                reviewController.createReview(authentication, dto));
        assertEquals("Review already exists", ex.getMessage());
    }

    @Test
    void testUpdateReviewWithException() {
        Long reviewId = 1L;
        ReviewDTO dto = new ReviewDTO();
        when(reviewService.updateReview(reviewId, userId, dto))
                .thenThrow(new IllegalArgumentException("Unauthorized"));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                reviewController.updateReview(reviewId, authentication, dto));
        assertEquals("Unauthorized", ex.getMessage());
    }

    @Test
    void testDeleteReviewWithException() {
        Long reviewId = 1L;
        doThrow(new IllegalArgumentException("Review not found"))
                .when(reviewService).deleteReview(reviewId, userId);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                reviewController.deleteReview(reviewId, authentication));
        assertEquals("Review not found", ex.getMessage());
    }

    @Test
    void testErrorResponseConstructorAndGetter() {
        String message = "Test error message";
        ReviewController.ErrorResponse errorResponse = new ReviewController.ErrorResponse(message);
        assertEquals(message, errorResponse.getMessage());
    }

    @Test
    void testCourseRatingStatsGetters() {
        Double averageRating = 4.5;
        Long totalReviews = 100L;
        CourseRatingStats stats = new CourseRatingStats(averageRating, totalReviews);

        assertEquals(averageRating, stats.getAverageRating());
        assertEquals(totalReviews, stats.getTotalReviews());
    }

    @Test
    void testGetUserIdFromAuthWithUserPrincipal() {
        // This is already covered in other tests, but adding explicit test
        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getId()).thenReturn(userId);

        List<ReviewDTO> mockReviews = List.of(new ReviewDTO());
        when(reviewService.getStudentReviews(userId)).thenReturn(mockReviews);

        List<ReviewDTO> result = reviewController.getMyReviews(authentication);
        assertEquals(mockReviews, result);
        verify(reviewService).getStudentReviews(userId);
    }
}
