package id.ac.ui.cs.advprog.udehnihh.course.repository;

import id.ac.ui.cs.advprog.udehnihh.course.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CbEnrollmentRepository
        extends JpaRepository<Enrollment, UUID>, JpaSpecificationExecutor<Enrollment> {
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);
    
    @Query("SELECT e FROM Enrollment e LEFT JOIN FETCH e.course WHERE e.student.id = :studentId")
    List<Enrollment> findByStudentIdWithCourse(@Param("studentId") UUID studentId);
    
    List<Enrollment> findByStudentId(UUID studentId);
}

