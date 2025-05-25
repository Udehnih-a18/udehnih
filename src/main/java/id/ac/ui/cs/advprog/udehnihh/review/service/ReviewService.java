package id.ac.ui.cs.advprog.udehnihh.review.service;

import id.ac.ui.cs.advprog.udehnihh.review.dto.ReviewDTO;
import id.ac.ui.cs.advprog.udehnihh.review.model.Review;
import id.ac.ui.cs.advprog.udehnihh.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    
    public ReviewDTO createReview(Long studentId, ReviewDTO dto) {
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
    public List<ReviewDTO> getCourseReviews(Long courseId) {
        return reviewRepository.findByCourseIdOrderByCreatedAtDesc(courseId)
            .stream()
            .map(this::toDTO)
            .toList();
    }
    
    @Transactional(readOnly = true)
    public List<ReviewDTO> getStudentReviews(Long studentId) {
        return reviewRepository.findByStudentIdOrderByCreatedAtDesc(studentId)
            .stream()
            .map(this::toDTO)
            .toList();
    }
    
    @Transactional(readOnly = true)
    public Optional<ReviewDTO> getStudentReviewForCourse(Long studentId, Long courseId) {
        return reviewRepository.findByStudentIdAndCourseId(studentId, courseId)
            .map(this::toDTO);
    }
    
    public ReviewDTO updateReview(Long reviewId, Long studentId, ReviewDTO dto) {
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
    
    public void deleteReview(Long reviewId, Long studentId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        
        if (!review.getStudentId().equals(studentId)) {
            throw new IllegalArgumentException("Unauthorized");
        }
        
        reviewRepository.delete(review);
    }
    
    @Transactional(readOnly = true)
    public CourseRatingStats getCourseRatingStats(Long courseId) {
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