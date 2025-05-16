package id.ac.ui.cs.advprog.udehnihh.course.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
<<<<<<< HEAD:src/test/java/id/ac/ui/cs/advprog/udehnihh/controller/CbCourseControllerTest.java
import id.ac.ui.cs.advprog.udehnihh.course.controller.CbCourseController;
=======
>>>>>>> origin/course:src/test/java/id/ac/ui/cs/advprog/udehnihh/course/controller/CbCourseControllerTest.java
import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseDetailDto;
import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseSummaryDto;
import id.ac.ui.cs.advprog.udehnihh.course.dto.EnrollmentDto;
import id.ac.ui.cs.advprog.udehnihh.course.service.CbCourseService;
import id.ac.ui.cs.advprog.udehnihh.course.service.CbEnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CbCourseControllerTest {

    @Mock
    private CbCourseService courseSvc;

    @Mock
    private CbEnrollmentService enrollSvc;

    @InjectMocks
    private CbCourseController controller;

    @Mock
    private User mockUser;

    private UUID testId;
    private UUID enrollmentId;
    private List<CourseSummaryDto> mockCourses;
    private CourseDetailDto mockDetail;
    private List<EnrollmentDto> mockEnrollments;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        testId = UUID.randomUUID();
        enrollmentId = UUID.randomUUID();
        
        // Setup mock data
        mockCourses = Arrays.asList(
            new CourseSummaryDto(UUID.randomUUID(), "Java Programming", "Learn Java", 99.99),
            new CourseSummaryDto(UUID.randomUUID(), "Spring Boot", "Learn Spring", 149.99)
        );
        
        mockDetail = new CourseDetailDto(
            testId,
            "Test Course",
            "Test Description",
            "Tutor Name",
            99.99,
            List.of()
        );
        
        mockEnrollments = Arrays.asList(
            new EnrollmentDto(UUID.randomUUID(), UUID.randomUUID(), "Course 1", null, "PAID"),
            new EnrollmentDto(UUID.randomUUID(), UUID.randomUUID(), "Course 2", null, "PAID")
        );
    }

    @Test
    void list_shouldReturnAllCourses_whenNoParameters() {
        // Given
        when(courseSvc.search(null, null, null)).thenReturn(mockCourses);

        // When
        List<CourseSummaryDto> result = controller.list(null, null, null);

        // Then
        assertEquals(mockCourses, result);
        verify(courseSvc).search(null, null, null);
    }

    @Test
    void list_shouldSearchWithParameters_whenParametersProvided() {
        // Given
        String query = "java";
        Double minPrice = 50.0;
        Double maxPrice = 200.0;
        when(courseSvc.search(query, minPrice, maxPrice)).thenReturn(mockCourses);

        // When
        List<CourseSummaryDto> result = controller.list(query, minPrice, maxPrice);

        // Then
        assertEquals(mockCourses, result);
        verify(courseSvc).search(query, minPrice, maxPrice);
    }

    @Test
    void detail_shouldReturnCourseDetail_whenCourseExists() {
        // Given
        when(courseSvc.getDetail(testId)).thenReturn(mockDetail);

        // When
        CourseDetailDto result = controller.detail(testId);

        // Then
        assertEquals(mockDetail, result);
        verify(courseSvc).getDetail(testId);
    }

    @Test
    void enroll_shouldReturnEnrollmentId_whenEnrollmentSuccessful() throws NoSuchMethodException, SecurityException {
        // Given
        when(enrollSvc.enroll(mockUser, testId)).thenReturn(enrollmentId);

        // When
        UUID result = controller.enroll(testId, mockUser);

        // Then
        assertEquals(enrollmentId, result);
        verify(enrollSvc).enroll(mockUser, testId);
        
        // Verify that the method is annotated with @ResponseStatus(HttpStatus.CREATED)
        ResponseStatus annotation = CbCourseController.class.getDeclaredMethod("enroll", UUID.class, User.class)
                .getAnnotation(ResponseStatus.class);
        assertEquals(HttpStatus.CREATED, annotation.value());
    }

    @Test
    void myCourses_shouldReturnUserEnrollments_whenCalled() {
        // Given
        when(enrollSvc.myCourses(mockUser)).thenReturn(mockEnrollments);

        // When
        List<EnrollmentDto> result = controller.myCourses(mockUser);

        // Then
        assertEquals(mockEnrollments, result);
        verify(enrollSvc).myCourses(mockUser);
    }
}