package id.ac.ui.cs.advprog.udehnihh.strategy;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import id.ac.ui.cs.advprog.udehnihh.repository.CourseRepository;
import java.util.List;

public class NameSearchStrategy implements CourseSearchStrategy {
    private final CourseRepository courseRepository;

    public NameSearchStrategy(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> search(String query) {
        return courseRepository.findByName(query);
    }
}