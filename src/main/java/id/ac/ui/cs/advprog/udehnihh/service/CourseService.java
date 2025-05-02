package id.ac.ui.cs.advprog.udehnihh.service;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import id.ac.ui.cs.advprog.udehnihh.repository.CourseRepository;
import id.ac.ui.cs.advprog.udehnihh.strategy.CourseSearchStrategy;
import java.util.List;
import java.util.Optional;

public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseSearchStrategy searchStrategy;

    public CourseService(CourseRepository courseRepository, CourseSearchStrategy searchStrategy) {
        this.courseRepository = courseRepository;
        this.searchStrategy = searchStrategy;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public List<Course> searchCourses(String query) {
        return searchStrategy.search(query);
    }
}