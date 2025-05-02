package id.ac.ui.cs.advprog.udehnihh.strategy;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import id.ac.ui.cs.advprog.udehnihh.repository.CourseRepository;
import java.util.List;

public class PriceRangeSearchStrategy implements CourseSearchStrategy {
    private final CourseRepository courseRepository;

    public PriceRangeSearchStrategy(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> search(String query) {
        // Assuming query format is "min-max"
        String[] parts = query.split("-");
        Double minPrice = Double.parseDouble(parts[0]);
        Double maxPrice = Double.parseDouble(parts[1]);
        return courseRepository.findByPriceRange(minPrice, maxPrice);
    }
}