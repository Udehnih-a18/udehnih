package id.ac.ui.cs.advprog.udehnihh.course.repository;

import id.ac.ui.cs.advprog.udehnihh.course.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface CbEnrollmentRepository
        extends JpaRepository<Enrollment, UUID>, JpaSpecificationExecutor<Enrollment> {
    boolean existsByStudentIdAndCourseId(UUID studentId, UUID courseId);
    List<Enrollment> findByStudentId(UUID studentId);
}

