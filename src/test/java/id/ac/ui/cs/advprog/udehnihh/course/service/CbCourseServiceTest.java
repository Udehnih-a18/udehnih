package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseDetailDto;
import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseSummaryDto;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CbCourseRepository;
<<<<<<< HEAD:src/test/java/id/ac/ui/cs/advprog/udehnihh/service/CbCourseServiceTest.java
import id.ac.ui.cs.advprog.udehnihh.course.service.CbCourseService;
=======
>>>>>>> e45c4ca00c105822c8133f268e61d8512744b901:src/test/java/id/ac/ui/cs/advprog/udehnihh/course/service/CbCourseServiceTest.java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CbCourseServiceTest {

    @Mock
    private CbCourseRepository courseRepo;

    @InjectMocks
    private CbCourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseService = new CbCourseService(courseRepo);
    }

    @Test
    void testSearch_ReturnsCourseSummaryDtoList() {
        Course course = new Course();
        course.setId(UUID.randomUUID());
        course.setName("Test Course");
        User tutor = new User();
        tutor.setFullName("Tutor Name");
        course.setTutor(tutor);
        course.setPrice(10000.0);

        when(courseRepo.findAll(any(Specification.class))).thenReturn(List.of(course));

        List<CourseSummaryDto> result = courseService.search("Test", 5000.0, 20000.0);

        assertEquals(1, result.size());
        assertEquals("Test Course", result.get(0).name());
        assertEquals("Tutor Name", result.get(0).tutorName());
    }

    @Test
    void testGetDetail_ReturnsCourseDetailDto() {
        UUID courseId = UUID.randomUUID();
        Course course = new Course();
        course.setId(courseId);
        course.setName("Detail Course");
        course.setDescription("desc");
        User tutor = new User();
        tutor.setFullName("Tutor Name");
        course.setTutor(tutor);
        course.setPrice(0.0);
        course.setSections(new ArrayList<>());

        when(courseRepo.findById(courseId)).thenReturn(Optional.of(course));

        CourseDetailDto result = courseService.getDetail(courseId);

        assertEquals("Detail Course", result.name());
        assertEquals("Tutor Name", result.tutorName());
        assertEquals(0, result.sections().size());
    }

    @Test
    void testGetDetail_CourseNotFound_ThrowsException() {
        UUID courseId = UUID.randomUUID();
        when(courseRepo.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> courseService.getDetail(courseId));
    }
}
