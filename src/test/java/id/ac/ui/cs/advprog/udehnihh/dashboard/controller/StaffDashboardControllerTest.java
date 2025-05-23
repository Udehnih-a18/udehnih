package id.ac.ui.cs.advprog.udehnihh.dashboard.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.course.enums.CourseStatus;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.repository.TutorApplicationRepository;
import id.ac.ui.cs.advprog.udehnihh.course.service.CourseCreationService;
import id.ac.ui.cs.advprog.udehnihh.course.service.TutorApplicationService;
import id.ac.ui.cs.advprog.udehnihh.dashboard.dto.TutorDetailRequest;
import id.ac.ui.cs.advprog.udehnihh.dashboard.dto.TutorListRequest;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import static id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication.ApplicationStatus.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffDashboardControllerTest {

    @InjectMocks
    private StaffDashboardController staffDashboardController;

    @Mock
    private TutorApplicationService tutorApplicationService;

    @Mock
    private CourseCreationService courseCreationService;

    @Mock
    private TutorApplicationRepository tutorApplicationRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTutorApplications_ReturnsList_Success() {
        List<TutorListRequest> expectedList = Collections.singletonList(new TutorListRequest());
        when(tutorApplicationRepository.findAllTutorApplicationsWithUserData()).thenReturn(expectedList);

        ResponseEntity<List<TutorListRequest>> response = staffDashboardController.getAllTutorApplications();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedList, response.getBody());
    }

    @Test
    void getTutorApplicationDetails_ApplicationFound_ReturnsDetails() {
        UUID applicationId = UUID.randomUUID();
        User applicant = new User();
        applicant.setFullName("John Doe");
        applicant.setEmail("john@example.com");
        applicant.setRegistrationDate(LocalDateTime.now());

        TutorApplication application = new TutorApplication();
        application.setApplicant(applicant);
        application.setStatus(PENDING);
        application.setCreatedAt(LocalDateTime.now());

        when(tutorApplicationRepository.findById(applicationId)).thenReturn(Optional.of(application));

        ResponseEntity<TutorDetailRequest> response = staffDashboardController.getTutorApplicationDetails(applicationId);
        TutorDetailRequest dto = response.getBody();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(applicant.getFullName(), dto.getFullName());
        assertEquals(applicant.getEmail(), dto.getEmail());
        assertEquals(application.getStatus(), dto.getStatus());
        assertEquals(applicant.getRegistrationDate(), dto.getRegistrationDate());
        assertEquals(application.getCreatedAt(), dto.getApplicationDate());
    }

    @Test
    void getTutorApplicationDetails_ApplicationNotFound_ThrowsException() {
        UUID applicationId = UUID.randomUUID();
        when(tutorApplicationRepository.findById(applicationId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            staffDashboardController.getTutorApplicationDetails(applicationId);
        });
    }

    @Test
    void approveTutorApplication_Success_ReturnsOk() {
        UUID applicationId = UUID.randomUUID();

        ResponseEntity<Void> response = staffDashboardController.approveTutorApplication(applicationId);

        assertEquals(200, response.getStatusCodeValue());
        verify(tutorApplicationService, times(1)).approveTutorApplication(applicationId);
    }

    @Test
    void rejectTutorApplication_Success_ReturnsOk() {
        UUID applicationId = UUID.randomUUID();
        String reason = "Insufficient qualifications";

        ResponseEntity<Void> response = staffDashboardController.rejectTutorApplication(applicationId, reason);

        assertEquals(200, response.getStatusCodeValue());
        verify(tutorApplicationService, times(1)).rejectTutorApplication(applicationId, reason);
    }

    @Test
    void getAllCourses_ReturnsList_Success() {
        CourseRequest courseRequest = new CourseRequest();
        courseRequest.setName("Test Course");
        courseRequest.setDescription("Test Description");
        courseRequest.setPrice(100000.0);
        courseRequest.setTutorId(UUID.randomUUID());
        courseRequest.setCourseStatus(CourseStatus.PENDING_APPROVAL);

        List<CourseRequest> expectedList = Collections.singletonList(courseRequest);
        when(courseCreationService.getAllCoursesDTO()).thenReturn(expectedList);

        ResponseEntity<List<CourseRequest>> response;
        response = staffDashboardController.getAllCourses();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedList, response.getBody());
        assertEquals("Test Course", response.getBody().get(0).getName());
        assertEquals(CourseStatus.PENDING_APPROVAL, response.getBody().get(0).getCourseStatus());
    }

    @Test
    void approveCourse_Success_ReturnsApprovedCourse() {
        UUID courseId = UUID.randomUUID();
        Course expectedCourse = new Course();
        when(courseCreationService.approveCourse(courseId)).thenReturn(expectedCourse);

        ResponseEntity<Course> response = staffDashboardController.approveCourse(courseId);

        assertEquals(200, response.getStatusCodeValue());
        assertSame(expectedCourse, response.getBody());
    }

    @Test
    void rejectCourse_Success_ReturnsRejectedCourse() {
        UUID courseId = UUID.randomUUID();
        Course expectedCourse = new Course();
        when(courseCreationService.rejectCourse(courseId)).thenReturn(expectedCourse);

        ResponseEntity<Course> response = staffDashboardController.rejectCourse(courseId);

        assertEquals(200, response.getStatusCodeValue());
        assertSame(expectedCourse, response.getBody());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
}