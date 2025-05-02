package id.ac.ui.cs.advprog.udehnihh.repository;

import id.ac.ui.cs.advprog.udehnihh.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    List<Course> findByTutorId(UUID tutorId);
}
