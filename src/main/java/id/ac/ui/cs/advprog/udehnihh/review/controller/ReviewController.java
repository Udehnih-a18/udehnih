package id.ac.ui.cs.advprog.udehnihh.review.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.udehnihh.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO createReview(
            Authentication auth,
            @Validated(ReviewDTO.Create.class) @RequestBody ReviewDTO dto) {
        
        UUID studentId = getUserIdFromAuth(auth);
        return reviewService.createReview(studentId, dto);
    }
    
    @GetMapping("/course/{courseId}")
    public List<ReviewDTO> getCourseReviews(@PathVariable UUID courseId) {
        return reviewService.getCourseReviews(courseId);
    }
    
    @GetMapping("/course/{courseId}/stats")
    public ReviewService.CourseRatingStats getCourseRatingStats(@PathVariable UUID courseId) {
        return reviewService.getCourseRatingStats(courseId);
    }
    
    @GetMapping("/student/my-reviews")
    public List<ReviewDTO> getMyReviews(Authentication auth) {
        UUID studentId = getUserIdFromAuth(auth);
        return reviewService.getStudentReviews(studentId);
    }
    
    @GetMapping("/course/{courseId}/my-review")
    public ResponseEntity<ReviewDTO> getMyReviewForCourse(
            @PathVariable UUID courseId, 
            Authentication auth) {
        UUID studentId = getUserIdFromAuth(auth);
        Optional<ReviewDTO> review = reviewService.getStudentReviewForCourse(studentId, courseId);
        return review.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{reviewId}")
    public ReviewDTO updateReview(
            @PathVariable Long reviewId,
            Authentication auth,
            @Valid @RequestBody ReviewDTO dto) {
        
        UUID studentId = getUserIdFromAuth(auth);
        return reviewService.updateReview(reviewId, studentId, dto);
    }
    
    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long reviewId, Authentication auth) {
        UUID studentId = getUserIdFromAuth(auth);
        reviewService.deleteReview(reviewId, studentId);
    }
    
    private UUID getUserIdFromAuth(Authentication auth) {
        if (auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            return user.getId();
        } else if (auth.getPrincipal() instanceof String) {
            return UUID.fromString((String) auth.getPrincipal());
        } else {
            throw new IllegalStateException("Unable to extract user ID from authentication");
        }
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException: {}", e.getMessage());
        return new ErrorResponse(e.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage(), e);
        return new ErrorResponse("Internal server error: " + e.getMessage());
    }
    
    public static class ErrorResponse {
        private final String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() { return message; }
    }
}