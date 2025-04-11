package id.ac.ui.cs.advprog.udehnihh.service;

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