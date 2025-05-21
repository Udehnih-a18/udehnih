package id.ac.ui.cs.advprog.udehnihh.course.controller;

import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.course.repository.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseCreationControllerTest {

    @InjectMocks
    private CourseCreationController courseController;

    @Mock
    private CourseCreationRepository courseRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    private AutoCloseable closeable;

    private UUID userId;
    private User tutor;
    private CourseRequest courseRequest;
    private Course course;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
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

        ResponseEntity<Course> response = courseController.createCourse(courseRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(course.getName(), response.getBody().getName());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testCreateCourseUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseEntity<Course> response = courseController.createCourse(courseRequest);

        assertEquals(400, response.getStatusCodeValue());
        verify(courseRepository, never()).save(any());
    }

    @Test
    void testGetCoursesByTutor() {
        UUID tutorId = UUID.randomUUID();
        List<Course> courseList = List.of(course);
        when(courseRepository.findByTutorId(tutorId)).thenReturn(courseList);

        ResponseEntity<List<Course>> response = courseController.getCoursesByTutor(tutorId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(courseList, response.getBody());
    }

    @Test
    void testGetCourseDetailFound() {
        UUID courseId = UUID.randomUUID();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        ResponseEntity<Course> response = courseController.getCourseDetail(courseId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(course.getName(), response.getBody().getName());
    }

    @Test
    void testGetCourseDetailNotFound() {
        UUID courseId = UUID.randomUUID();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        ResponseEntity<Course> response = courseController.getCourseDetail(courseId);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteCourseSuccess() {
        UUID courseId = UUID.randomUUID();
        when(courseRepository.existsById(courseId)).thenReturn(true);
        doNothing().when(courseRepository).deleteById(courseId);

        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        assertEquals(204, response.getStatusCodeValue());
        verify(courseRepository).deleteById(courseId);
    }

    @Test
    void testDeleteCourseNotFound() {
        UUID courseId = UUID.randomUUID();
        when(courseRepository.existsById(courseId)).thenReturn(false);

        ResponseEntity<Void> response = courseController.deleteCourse(courseId);

        assertEquals(404, response.getStatusCodeValue());
        verify(courseRepository, never()).deleteById(courseId);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
}