package id.ac.ui.cs.advprog.udehnihh.course.repository;

import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.UUID;

public interface CbCourseRepository
       extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {
}
