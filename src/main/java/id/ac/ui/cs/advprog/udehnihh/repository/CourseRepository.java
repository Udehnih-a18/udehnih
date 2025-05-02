package id.ac.ui.cs.advprog.udehnihh.repository;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import java.util.List;
import java.util.Optional;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

public interface CourseRepository {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    List<Course> findByName(String name);
    List<Course> findByTutor(User tutor);
    List<Course> findByPriceRange(Double minPrice, Double maxPrice);
    Course save(Course course);
}
