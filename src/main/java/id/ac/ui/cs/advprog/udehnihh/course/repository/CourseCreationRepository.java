package id.ac.ui.cs.advprog.udehnihh.course.repository;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseCreationRepository extends JpaRepository<Course, UUID> {
    List<Course> findByTutorId(UUID tutorId);
    List<Course> findByStatus(Course.CourseStatus status);
}
