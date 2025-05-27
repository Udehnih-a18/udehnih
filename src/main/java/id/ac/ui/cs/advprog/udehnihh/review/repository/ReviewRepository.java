package id.ac.ui.cs.advprog.udehnihh.review.repository;

import id.ac.ui.cs.advprog.udehnihh.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByCourseIdOrderByCreatedAtDesc(UUID courseId);  // Changed from Long to UUID
    
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);  // Changed courseId from Long to UUID
    
    Optional<Review> findByStudentIdAndCourseId(UUID studentId, UUID courseId);  // Changed courseId from Long to UUID
    
    List<Review> findByStudentIdOrderByCreatedAtDesc(UUID studentId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.courseId = :courseId")
    Double findAverageRatingByCourseId(@Param("courseId") UUID courseId);  // Changed from Long to UUID
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.courseId = :courseId")
    Long countByCourseId(@Param("courseId") UUID courseId);  // Changed from Long to UUID
}