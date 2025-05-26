package id.ac.ui.cs.advprog.udehnihh.course.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
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
import org.springframework.http.ResponseEntity;

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
    void enroll_shouldReturnCreated_whenEnrollmentSuccessful() {
        // Given
        when(enrollSvc.enroll(mockUser, testId)).thenReturn(enrollmentId);

        // When
        ResponseEntity<?> result = controller.enroll(testId, mockUser);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(enrollmentId, result.getBody());
        verify(enrollSvc).enroll(mockUser, testId);
    }

    @Test
    void enroll_shouldReturnBadRequest_whenIllegalStateException() {
        // Given
        when(enrollSvc.enroll(mockUser, testId)).thenThrow(new IllegalStateException("Already enrolled"));

        // When
        ResponseEntity<?> result = controller.enroll(testId, mockUser);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Already enrolled", result.getBody());
        verify(enrollSvc).enroll(mockUser, testId);
    }

    @Test
    void enroll_shouldReturnBadRequest_whenIllegalArgumentException() {
        // Given
        when(enrollSvc.enroll(mockUser, testId)).thenThrow(new IllegalArgumentException("Course not found"));

        // When
        ResponseEntity<?> result = controller.enroll(testId, mockUser);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Course not found", result.getBody());
        verify(enrollSvc).enroll(mockUser, testId);
    }

    @Test
    void enroll_shouldReturnUnauthorized_whenUserIsNull() {
        // When
        ResponseEntity<?> result = controller.enroll(testId, null);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals("Anda perlu login terlebih dahulu", result.getBody());
        verify(enrollSvc, never()).enroll(any(), any());
    }

    @Test
    void myCourses_shouldReturnUserEnrollments_whenUserAuthenticated() {
        // Given
        when(enrollSvc.myCourses(mockUser)).thenReturn(mockEnrollments);

        // When
        ResponseEntity<?> result = controller.myCourses(mockUser);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mockEnrollments, result.getBody());
        verify(enrollSvc).myCourses(mockUser);
    }

    @Test
    void myCourses_shouldReturnUnauthorized_whenUserIsNull() {
        // When
        ResponseEntity<?> result = controller.myCourses(null);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals("Anda perlu login terlebih dahulu", result.getBody());
        verify(enrollSvc, never()).myCourses(any());
    }
}