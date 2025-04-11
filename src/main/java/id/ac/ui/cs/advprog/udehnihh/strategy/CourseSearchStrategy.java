package id.ac.ui.cs.advprog.udehnihh.strategy;

public interface CourseSearchStrategy {
    List<Course> search(String query);
}

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