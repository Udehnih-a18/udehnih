package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.ArticleRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.SectionRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.response.CourseResponse;
import id.ac.ui.cs.advprog.udehnihh.course.model.Article;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.course.model.Section;
import id.ac.ui.cs.advprog.udehnihh.course.repository.ArticleRepository;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CourseCreationRepository;
import id.ac.ui.cs.advprog.udehnihh.course.repository.SectionRepository;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseCreationRepository courseRepository;

    @Mock
    private SectionRepository sectionRepository;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private User tutor;
    private UUID tutorId;
    private CourseRequest courseRequest;

    @BeforeEach
    void setUp() {
        tutorId = UUID.randomUUID();
        tutor = new User();
        tutor.setId(tutorId);
        tutor.setFullName("Tutor Name");

        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setContentTitle("Intro");
        articleRequest.setContentType("VIDEO");
        articleRequest.setContentText("Introduction to course");
        articleRequest.setContentUrl("http://example.com");
        articleRequest.setContentDescription("Desc");

        SectionRequest sectionRequest = new SectionRequest();
        sectionRequest.setName("Section 1");
        sectionRequest.setDescription("Basics");
        sectionRequest.setArticles(List.of(articleRequest));

        courseRequest = new CourseRequest();
        courseRequest.setName("Test Course");
        courseRequest.setDescription("Course Description");
        courseRequest.setPrice(100000.0);
        courseRequest.setTutorId(tutorId);
        courseRequest.setSections(List.of(sectionRequest));
    }

    @Test
    void testCreateFullCourseSuccess() {
        when(userRepository.findById(tutorId)).thenReturn(Optional.of(tutor));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));
        when(sectionRepository.save(any(Section.class))).thenAnswer(i -> i.getArgument(0));
        when(articleRepository.save(any(Article.class))).thenAnswer(i -> i.getArgument(0));

        CourseResponse response = courseService.createFullCourse(courseRequest);

        assertNotNull(response);
        assertEquals("Test Course", response.getName());
        verify(courseRepository).save(any(Course.class));
        verify(sectionRepository).save(any(Section.class));
        verify(articleRepository).save(any(Article.class));
    }

    @Test
    void testCreateFullCourseTutorNotFound() {
        when(userRepository.findById(tutorId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            courseService.createFullCourse(courseRequest);
        });

        assertEquals("Tutor not found", exception.getMessage());
    }

    @Test
    void testGetCoursesByTutor() {
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setName("Course A");
        course.setTutor(tutor);
        when(courseRepository.findByTutorId(tutorId)).thenReturn(List.of(course));

        List<CourseResponse> result = courseService.getCoursesByTutor(tutorId);

        assertEquals(1, result.size());
        assertEquals("Course A", result.get(0).getName());
    }

    @Test
    void testGetCourseByIdSuccess() {
        UUID courseId = UUID.randomUUID();
        Course course = new Course();
        course.setId(courseId);
        course.setName("Sample Course");
        course.setTutor(tutor);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Optional<CourseResponse> response = courseService.getCourseById(courseId);

        assertTrue(response.isPresent());
        assertEquals("Sample Course", response.get().getName());
    }

    @Test
    void testGetCourseByIdNotFound() {
        UUID courseId = UUID.randomUUID();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        Optional<CourseResponse> response = courseService.getCourseById(courseId);

        assertTrue(response.isEmpty());
    }

    @Test
    void testUpdateCourseSuccess() {
        UUID courseId = UUID.randomUUID();
        Course course = new Course();
        course.setId(courseId);
        course.setName("Old Name");
        course.setTutor(tutor);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));

        CourseRequest updateRequest = new CourseRequest();
        updateRequest.setName("New Name");
        updateRequest.setDescription("New Desc");
        updateRequest.setPrice(200000.0);

        CourseResponse response = courseService.updateCourse(courseId, updateRequest);

        assertEquals("New Name", response.getName());
        assertEquals(200000.0, response.getPrice());
    }

    @Test
    void testUpdateCourseNotFound() {
        UUID courseId = UUID.randomUUID();
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        CourseRequest updateRequest = new CourseRequest();
        updateRequest.setName("Update");
        updateRequest.setDescription("Desc");
        updateRequest.setPrice(150000.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseService.updateCourse(courseId, updateRequest));

        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    void testDeleteCourseSuccess() {
        UUID courseId = UUID.randomUUID();
        when(courseRepository.existsById(courseId)).thenReturn(true);

        courseService.deleteCourse(courseId);

        verify(courseRepository).deleteById(courseId);
    }

    @Test
    void testDeleteCourseNotFound() {
        UUID courseId = UUID.randomUUID();
        when(courseRepository.existsById(courseId)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                courseService.deleteCourse(courseId));

        assertEquals("Course not found", exception.getMessage());
    }
}
