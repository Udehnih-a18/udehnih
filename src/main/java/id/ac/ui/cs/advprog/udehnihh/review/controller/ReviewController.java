package id.ac.ui.cs.advprog.udehnihh.review.controller;

import id.ac.ui.cs.advprog.udehnihh.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.udehnihh.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO createReview(
            Authentication auth,
            @Validated(ReviewDTO.Create.class) @RequestBody ReviewDTO dto) {
        
        Long studentId = Long.valueOf(auth.getName());
        return reviewService.createReview(studentId, dto);
    }
    
    @GetMapping("/course/{courseId}")
    public List<ReviewDTO> getCourseReviews(@PathVariable Long courseId) {
        return reviewService.getCourseReviews(courseId);
    }
    
    @GetMapping("/course/{courseId}/stats")
    public ReviewService.CourseRatingStats getCourseRatingStats(@PathVariable Long courseId) {
        return reviewService.getCourseRatingStats(courseId);
    }
    
    @GetMapping("/student/my-reviews")
    public List<ReviewDTO> getMyReviews(Authentication auth) {
        Long studentId = Long.valueOf(auth.getName());
        return reviewService.getStudentReviews(studentId);
    }
    
    @GetMapping("/course/{courseId}/my-review")
    public ResponseEntity<ReviewDTO> getMyReviewForCourse(
            @PathVariable Long courseId, 
            Authentication auth) {
        Long studentId = Long.valueOf(auth.getName());
        Optional<ReviewDTO> review = reviewService.getStudentReviewForCourse(studentId, courseId);
        return review.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{reviewId}")
    public ReviewDTO updateReview(
            @PathVariable Long reviewId,
            Authentication auth,
            @Valid @RequestBody ReviewDTO dto) {
        
        Long studentId = Long.valueOf(auth.getName());
        return reviewService.updateReview(reviewId, studentId, dto);
    }
    
    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long reviewId, Authentication auth) {
        Long studentId = Long.valueOf(auth.getName());
        reviewService.deleteReview(reviewId, studentId);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse(e.getMessage());
    }
    
    public static class ErrorResponse {
        private final String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
        
        public String getMessage() { return message; }
    }
}