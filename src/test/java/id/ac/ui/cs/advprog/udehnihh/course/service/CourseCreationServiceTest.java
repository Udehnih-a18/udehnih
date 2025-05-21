package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CourseCreationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

class CourseCreationServiceTest {

    @InjectMocks
    private CourseCreationService courseCreationService;

    @Mock
    private CourseCreationRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    private UUID userId;
    private User tutor;
    private CourseRequest courseRequest;
    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();
        tutor = new User();
        tutor.setId(userId);

        courseRequest = new CourseRequest();
        courseRequest.setName("Course Name");
        courseRequest.setDescription("Course Desc");
        courseRequest.setPrice(100000.0);
        courseRequest.setTutorId(userId);

        course = new Course();
        course.setId(UUID.randomUUID());
        course.setName(courseRequest.getName());
        course.setDescription(courseRequest.getDescription());
        course.setPrice(courseRequest.getPrice());
        course.setTutor(tutor);
    }

    @Test
    void testCreateCourseSuccess() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(tutor));

        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course createdCourse = courseCreationService.createCourse(courseRequest);

        assertNotNull(createdCourse);
        assertEquals(courseRequest.getName(), createdCourse.getName());
        assertEquals(courseRequest.getDescription(), createdCourse.getDescription());
        assertEquals(courseRequest.getPrice(), createdCourse.getPrice());

        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testCreateCourseUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            courseCreationService.createCourse(courseRequest);
        });

        assertEquals("Tutor not found", exception.getMessage());
        verify(courseRepository, never()).save(any());
    }

    @Test
    void testCreateCourseCourseName() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(tutor));

        when(courseRepository.save(any(Course.class))).thenReturn(course);

        Course createdCourse = courseCreationService.createCourse(courseRequest);
        assertEquals("Course Name", createdCourse.getName());
    }
}