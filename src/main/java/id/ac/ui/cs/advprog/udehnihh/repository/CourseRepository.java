package id.ac.ui.cs.advprog.udehnihh.repository;

public interface CourseRepository {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    List<Course> findByName(String name);
    List<Course> findByTutor(User tutor);
    List<Course> findByPriceRange(Double minPrice, Double maxPrice);
    Course save(Course course);
}
