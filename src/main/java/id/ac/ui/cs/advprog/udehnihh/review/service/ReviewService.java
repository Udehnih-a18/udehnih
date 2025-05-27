package id.ac.ui.cs.advprog.udehnihh.review.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.udehnihh.review.model.Review;
import id.ac.ui.cs.advprog.udehnihh.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;  // Add UserRepository
    
    public ReviewDTO createReview(UUID studentId, ReviewDTO dto) {
        if (reviewRepository.existsByStudentIdAndCourseId(studentId, dto.getCourseId())) {
            throw new IllegalArgumentException("Review already exists");
        }
        
        Review review = Review.builder()
            .studentId(studentId)
            .courseId(dto.getCourseId())
            .rating(dto.getRating())
            .comment(dto.getComment())
            .build();
        
        Review saved = reviewRepository.save(review);
        return toDTO(saved);
    }
    
    @Transactional(readOnly = true)
    public List<ReviewDTO> getCourseReviews(UUID courseId) {
        List<Review> reviews = reviewRepository.findByCourseIdOrderByCreatedAtDesc(courseId);
        return reviews.stream()
            .map(this::toDTOWithReviewerName)
            .toList();
    }
    
    @Transactional(readOnly = true)
    public List<ReviewDTO> getStudentReviews(UUID studentId) {
        List<Review> reviews = reviewRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
        return reviews.stream()
            .map(this::toDTO)
            .toList();
    }
    
    @Transactional(readOnly = true)
    public Optional<ReviewDTO> getStudentReviewForCourse(UUID studentId, UUID courseId) {
        return reviewRepository.findByStudentIdAndCourseId(studentId, courseId)
            .map(this::toDTO);
    }
    
    public ReviewDTO updateReview(Long reviewId, UUID studentId, ReviewDTO dto) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        
        if (!review.getStudentId().equals(studentId)) {
            throw new IllegalArgumentException("Unauthorized");
        }
        
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        
        Review updated = reviewRepository.save(review);
        return toDTO(updated);
    }
    
    public void deleteReview(Long reviewId, UUID studentId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        
        if (!review.getStudentId().equals(studentId)) {
            throw new IllegalArgumentException("Unauthorized");
        }
        
        reviewRepository.delete(review);
    }
    
    @Transactional(readOnly = true)
    public CourseRatingStats getCourseRatingStats(UUID courseId) {
        Double averageRating = reviewRepository.findAverageRatingByCourseId(courseId);
        Long totalReviews = reviewRepository.countByCourseId(courseId);
        
        return new CourseRatingStats(
            averageRating != null ? averageRating : 0.0,
            totalReviews != null ? totalReviews : 0L
        );
    }
    
    private ReviewDTO toDTO(Review review) {
        return ReviewDTO.builder()
            .id(review.getId())
            .studentId(review.getStudentId())
            .courseId(review.getCourseId())
            .rating(review.getRating())
            .comment(review.getComment())
            .createdAt(review.getCreatedAt())
            .updatedAt(review.getUpdatedAt())
            .build();
    }
    
    private ReviewDTO toDTOWithReviewerName(Review review) {
        String reviewerName = userRepository.findById(review.getStudentId())
            .map(user -> user.getFullName())
            .orElse("Anonymous");
            
        return ReviewDTO.builder()
            .id(review.getId())
            .studentId(review.getStudentId())
            .courseId(review.getCourseId())
            .rating(review.getRating())
            .comment(review.getComment())
            .reviewerName(reviewerName)
            .createdAt(review.getCreatedAt())
            .updatedAt(review.getUpdatedAt())
            .build();
    }
    
    public static class CourseRatingStats {
        private final Double averageRating;
        private final Long totalReviews;
        
        public CourseRatingStats(Double averageRating, Long totalReviews) {
            this.averageRating = averageRating;
            this.totalReviews = totalReviews;
        }
        
        public Double getAverageRating() { return averageRating; }
        public Long getTotalReviews() { return totalReviews; }
    }
}