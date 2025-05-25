package id.ac.ui.cs.advprog.udehnihh.review.repository;

import id.ac.ui.cs.advprog.udehnihh.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByCourseIdOrderByCreatedAtDesc(Long courseId);
    
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
    
    Optional<Review> findByStudentIdAndCourseId(Long studentId, Long courseId);
    
    List<Review> findByStudentIdOrderByCreatedAtDesc(Long studentId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.courseId = :courseId")
    Double findAverageRatingByCourseId(@Param("courseId") Long courseId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.courseId = :courseId")
    Long countByCourseId(@Param("courseId") Long courseId);
}