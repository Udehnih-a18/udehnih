package id.ac.ui.cs.advprog.udehnihh.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CourseServiceTest {
    private CourseRepository courseRepository;
    private CourseSearchStrategy searchStrategy;
    private CourseService courseService;

    @BeforeEach
    public void setup() {
        courseRepository = mock(CourseRepository.class);
        searchStrategy = mock(CourseSearchStrategy.class);
        courseService = new CourseService(courseRepository, searchStrategy);
    }

    @Test
    public void testGetAllCourses() {
        // Arrange
        List<Course> courses = Arrays.asList(
                new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>()),
                new Course(2L, "Web Development", "Learn HTML, CSS, JS", new User(), 75000.0, new ArrayList<>())
        );
        when(courseRepository.findAll()).thenReturn(courses);

        // Act
        List<Course> result = courseService.getAllCourses();

        // Assert
        assertEquals(2, result.size());
        verify(courseRepository).findAll();
    }

    @Test
    public void testGetCourseById() {
        // Arrange
        Course course = new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>());
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Act
        Optional<Course> result = courseService.getCourseById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Java Basic", result.get().getName());
        verify(courseRepository).findById(1L);
    }

    @Test
    public void testSearchCourses() {
        // Arrange
        List<Course> courses = Arrays.asList(
                new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>())
        );
        when(searchStrategy.search("Java")).thenReturn(courses);

        // Act
        List<Course> result = courseService.searchCourses("Java");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Java Basic", result.get(0).getName());
        verify(searchStrategy).search("Java");
    }
}