package id.ac.ui.cs.advprog.udehnihh.course.repository;

import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CbCourseRepository
        extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {
    
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.tutor WHERE c.id = :id")
    Optional<Course> findByIdWithTutor(@Param("id") UUID id);
    
    @Query("SELECT c FROM Course c " +
           "LEFT JOIN FETCH c.tutor " +
           "LEFT JOIN FETCH c.sections s " +
           "LEFT JOIN FETCH s.sectionContents " +
           "WHERE c.id = :id")
    Optional<Course> findByIdWithFullDetails(@Param("id") UUID id);
}
