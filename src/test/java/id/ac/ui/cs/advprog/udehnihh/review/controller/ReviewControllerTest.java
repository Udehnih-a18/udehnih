package id.ac.ui.cs.advprog.udehnihh.review.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.udehnihh.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.udehnihh.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
@Import(TestSecurityConfig.class)
class ReviewControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ReviewService reviewService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private ReviewDTO sampleReviewDTO;
    
    @BeforeEach
    void setUp() {
        sampleReviewDTO = ReviewDTO.builder()
            .id(1L)
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Excellent course!")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }
    
    @Test
    @DisplayName("Should create review successfully")
    @WithMockUser(username = "1")
    void shouldCreateReviewSuccessfully() throws Exception {
        // Given
        ReviewDTO requestDTO = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment("Excellent course!")
            .build();
        
        when(reviewService.createReview(eq(1L), any(ReviewDTO.class)))
            .thenReturn(sampleReviewDTO);
        
        // When & Then
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.studentId").value(1L))
                .andExpect(jsonPath("$.courseId").value(1L))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Excellent course!"));
        
        verify(reviewService).createReview(eq(1L), any(ReviewDTO.class));
    }
    
    @Test
    @DisplayName("Should return 400 for invalid create request")
    @WithMockUser(username = "1")
    void shouldReturn400ForInvalidCreateRequest() throws Exception {
        // Given - Invalid DTO with rating = 6 (exceeds max)
        ReviewDTO invalidDTO = ReviewDTO.builder()
            .courseId(1L)
            .rating(6) // Invalid rating
            .comment("Invalid rating")
            .build();
        
        // When & Then
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
        
        verifyNoInteractions(reviewService);
    }
    
    @Test
    @DisplayName("Should return 400 for missing courseId in create request")
    @WithMockUser(username = "1")
    void shouldReturn400ForMissingCourseIdInCreateRequest() throws Exception {
        // Given - Missing courseId for Create group validation
        ReviewDTO invalidDTO = ReviewDTO.builder()
            .rating(5)
            .comment("Missing courseId")
            .build();
        
        // When & Then
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
        
        verifyNoInteractions(reviewService);
    }
    
    @Test
    @DisplayName("Should return 400 when service throws IllegalArgumentException")
    @WithMockUser(username = "1")
    void shouldReturn400WhenServiceThrowsIllegalArgumentException() throws Exception {
        // Given
        ReviewDTO requestDTO = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment("Duplicate review")
            .build();
        
        when(reviewService.createReview(eq(1L), any(ReviewDTO.class)))
            .thenThrow(new IllegalArgumentException("Review already exists"));
        
        // When & Then
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Review already exists"));
    }
    
    @Test
    @DisplayName("Should get course reviews successfully")
    void shouldGetCourseReviewsSuccessfully() throws Exception {
        // Given
        Long courseId = 1L;
        List<ReviewDTO> reviews = Arrays.asList(
            sampleReviewDTO,
            ReviewDTO.builder()
                .id(2L)
                .studentId(2L)
                .courseId(1L)
                .rating(4)
                .comment("Good course")
                .build()
        );
        
        when(reviewService.getCourseReviews(courseId)).thenReturn(reviews);
        
        // When & Then
        mockMvc.perform(get("/api/reviews/course/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].rating").value(5))
                .andExpect(jsonPath("$[0].comment").value("Excellent course!"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].rating").value(4))
                .andExpect(jsonPath("$[1].comment").value("Good course"));
        
        verify(reviewService).getCourseReviews(courseId);
    }
    
    @Test
    @DisplayName("Should get course rating stats successfully")
    void shouldGetCourseRatingStatsSuccessfully() throws Exception {
        // Given
        Long courseId = 1L;
        ReviewService.CourseRatingStats stats = new ReviewService.CourseRatingStats(4.5, 10L);
        
        when(reviewService.getCourseRatingStats(courseId)).thenReturn(stats);
        
        // When & Then
        mockMvc.perform(get("/api/reviews/course/{courseId}/stats", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageRating").value(4.5))
                .andExpect(jsonPath("$.totalReviews").value(10));
        
        verify(reviewService).getCourseRatingStats(courseId);
    }
    
    @Test
    @DisplayName("Should get my reviews successfully")
    @WithMockUser(username = "1")
    void shouldGetMyReviewsSuccessfully() throws Exception {
        // Given
        List<ReviewDTO> reviews = Arrays.asList(sampleReviewDTO);
        
        when(reviewService.getStudentReviews(1L)).thenReturn(reviews);
        
        // When & Then
        mockMvc.perform(get("/api/reviews/student/my-reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L));
        
        verify(reviewService).getStudentReviews(1L);
    }
    
    @Test
    @DisplayName("Should get my review for course successfully")
    @WithMockUser(username = "1")
    void shouldGetMyReviewForCourseSuccessfully() throws Exception {
        // Given
        Long courseId = 1L;
        
        when(reviewService.getStudentReviewForCourse(1L, courseId))
            .thenReturn(Optional.of(sampleReviewDTO));
        
        // When & Then
        mockMvc.perform(get("/api/reviews/course/{courseId}/my-review", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        
        verify(reviewService).getStudentReviewForCourse(1L, courseId);
    }
    
    @Test
    @DisplayName("Should return 404 when my review for course not found")
    @WithMockUser(username = "1")
    void shouldReturn404WhenMyReviewForCourseNotFound() throws Exception {
        // Given
        Long courseId = 1L;
        
        when(reviewService.getStudentReviewForCourse(1L, courseId))
            .thenReturn(Optional.empty());
        
        // When & Then
        mockMvc.perform(get("/api/reviews/course/{courseId}/my-review", courseId))
                .andExpect(status().isNotFound());
        
        verify(reviewService).getStudentReviewForCourse(1L, courseId);
    }
    
    @Test
    @DisplayName("Should return empty list when no reviews found")
    void shouldReturnEmptyListWhenNoReviewsFound() throws Exception {
        // Given
        Long courseId = 999L;
        when(reviewService.getCourseReviews(courseId)).thenReturn(Arrays.asList());
        
        // When & Then
        mockMvc.perform(get("/api/reviews/course/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        
        verify(reviewService).getCourseReviews(courseId);
    }
    
    @Test
    @DisplayName("Should update review successfully")
    @WithMockUser(username = "1")
    void shouldUpdateReviewSuccessfully() throws Exception {
        // Given
        Long reviewId = 1L;
        ReviewDTO updateRequest = ReviewDTO.builder()
            .rating(4)
            .comment("Updated review")
            .build();
        
        ReviewDTO updatedResponse = ReviewDTO.builder()
            .id(reviewId)
            .studentId(1L)
            .courseId(1L)
            .rating(4)
            .comment("Updated review")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        
        when(reviewService.updateReview(eq(reviewId), eq(1L), any(ReviewDTO.class)))
            .thenReturn(updatedResponse);
        
        // When & Then
        mockMvc.perform(put("/api/reviews/{reviewId}", reviewId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reviewId))
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comment").value("Updated review"));
        
        verify(reviewService).updateReview(eq(reviewId), eq(1L), any(ReviewDTO.class));
    }
    
    @Test
    @DisplayName("Should return 400 for invalid update request")
    @WithMockUser(username = "1")
    void shouldReturn400ForInvalidUpdateRequest() throws Exception {
        // Given - Invalid rating
        Long reviewId = 1L;
        ReviewDTO invalidUpdateRequest = ReviewDTO.builder()
            .rating(0) // Invalid rating
            .comment("Invalid update")
            .build();
        
        // When & Then
        mockMvc.perform(put("/api/reviews/{reviewId}", reviewId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUpdateRequest)))
                .andExpect(status().isBadRequest());
        
        verifyNoInteractions(reviewService);
    }
    
    @Test
    @DisplayName("Should return 400 when unauthorized to update")
    @WithMockUser(username = "1")
    void shouldReturn400WhenUnauthorizedToUpdate() throws Exception {
        // Given
        Long reviewId = 1L;
        ReviewDTO updateRequest = ReviewDTO.builder()
            .rating(4)
            .comment("Unauthorized update")
            .build();
        
        when(reviewService.updateReview(eq(reviewId), eq(1L), any(ReviewDTO.class)))
            .thenThrow(new IllegalArgumentException("Unauthorized"));
        
        // When & Then
        mockMvc.perform(put("/api/reviews/{reviewId}", reviewId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Unauthorized"));
    }
    
    @Test
    @DisplayName("Should delete review successfully")
    @WithMockUser(username = "1")
    void shouldDeleteReviewSuccessfully() throws Exception {
        // Given
        Long reviewId = 1L;
        doNothing().when(reviewService).deleteReview(reviewId, 1L);
        
        // When & Then
        mockMvc.perform(delete("/api/reviews/{reviewId}", reviewId))
                .andExpect(status().isNoContent());
        
        verify(reviewService).deleteReview(reviewId, 1L);
    }
    
    @Test
    @DisplayName("Should return 400 when unauthorized to delete")
    @WithMockUser(username = "1")
    void shouldReturn400WhenUnauthorizedToDelete() throws Exception {
        // Given
        Long reviewId = 1L;
        doThrow(new IllegalArgumentException("Unauthorized"))
            .when(reviewService).deleteReview(reviewId, 1L);
        
        // When & Then
        mockMvc.perform(delete("/api/reviews/{reviewId}", reviewId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Unauthorized"));
    }
    
    @Test
    @DisplayName("Should return 400 when review not found for deletion")
    @WithMockUser(username = "1")
    void shouldReturn400WhenReviewNotFoundForDeletion() throws Exception {
        // Given
        Long reviewId = 999L;
        doThrow(new IllegalArgumentException("Review not found"))
            .when(reviewService).deleteReview(reviewId, 1L);
        
        // When & Then
        mockMvc.perform(delete("/api/reviews/{reviewId}", reviewId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Review not found"));
    }
    
    @Test
    @DisplayName("Should require authentication for create review")
    void shouldRequireAuthenticationForCreateReview() throws Exception {
        // Given
        ReviewDTO requestDTO = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment("Unauthenticated request")
            .build();
        
        // When & Then
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isUnauthorized());
        
        verifyNoInteractions(reviewService);
    }
    
    @Test
    @DisplayName("Should require authentication for update review")
    void shouldRequireAuthenticationForUpdateReview() throws Exception {
        // Given
        Long reviewId = 1L;
        ReviewDTO updateRequest = ReviewDTO.builder()
            .rating(4)
            .comment("Unauthenticated update")
            .build();
        
        // When & Then
        mockMvc.perform(put("/api/reviews/{reviewId}", reviewId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isUnauthorized());
        
        verifyNoInteractions(reviewService);
    }
    
    @Test
    @DisplayName("Should require authentication for delete review")
    void shouldRequireAuthenticationForDeleteReview() throws Exception {
        // Given
        Long reviewId = 1L;
        
        // When & Then
        mockMvc.perform(delete("/api/reviews/{reviewId}", reviewId))
                .andExpect(status().isUnauthorized());
        
        verifyNoInteractions(reviewService);
    }
    
    @Test
    @DisplayName("Should allow unauthenticated access to get course reviews")
    void shouldAllowUnauthenticatedAccessToGetCourseReviews() throws Exception {
        // Given
        Long courseId = 1L;
        when(reviewService.getCourseReviews(courseId)).thenReturn(Arrays.asList(sampleReviewDTO));
        
        // When & Then
        mockMvc.perform(get("/api/reviews/course/{courseId}", courseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
        
        verify(reviewService).getCourseReviews(courseId);
    }
    
    @Test
    @DisplayName("Should handle malformed JSON request")
    @WithMockUser(username = "1")
    void shouldHandleMalformedJSONRequest() throws Exception {
        // Given - Malformed JSON
        String malformedJson = "{ \"courseId\": 1, \"rating\": 5, \"comment\": }"; // Missing value
        
        // When & Then
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
                .andExpect(status().isBadRequest());
        
        verifyNoInteractions(reviewService);
    }
    
    @Test
    @DisplayName("Should handle empty request body")
    @WithMockUser(username = "1")
    void shouldHandleEmptyRequestBody() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
        
        verifyNoInteractions(reviewService);
    }
    
    @Test
    @DisplayName("Should handle invalid path variable")
    @WithMockUser(username = "1")
    void shouldHandleInvalidPathVariable() throws Exception {
        // When & Then
        mockMvc.perform(put("/api/reviews/invalid")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ReviewDTO.builder().rating(5).build())))
                .andExpect(status().isBadRequest());
        
        verifyNoInteractions(reviewService);
    }
    
    @Test
    @DisplayName("Should validate content type")
    @WithMockUser(username = "1")
    void shouldValidateContentType() throws Exception {
        // Given
        ReviewDTO requestDTO = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment("Wrong content type")
            .build();
        
        // When & Then - Send as plain text instead of JSON
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.TEXT_PLAIN)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isUnsupportedMediaType());
        
        verifyNoInteractions(reviewService);
    }
    
    @Test
    @DisplayName("Should handle HTTP method not allowed")
    void shouldHandleHttpMethodNotAllowed() throws Exception {
        // When & Then - Use PATCH instead of supported methods
        mockMvc.perform(patch("/api/reviews/1"))
                .andExpect(status().isMethodNotAllowed());
        
        verifyNoInteractions(reviewService);
    }
}