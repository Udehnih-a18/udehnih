package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.course.dto.EnrollmentDto;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.course.model.Enrollment;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CbCourseRepository;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CbEnrollmentRepository;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.service.CbEnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CbEnrollmentServiceTest {

    @Mock
    private CbEnrollmentRepository enrollRepo;
    @Mock
    private CbCourseRepository courseRepo;
    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CbEnrollmentService cbEnrollmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cbEnrollmentService = new CbEnrollmentService(enrollRepo, courseRepo, eventPublisher);
    }

    @Test
    void testEnroll_FreeCourse_Success() {
        User student = mock(User.class);
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        when(student.getId()).thenReturn(studentId);

        Course course = new Course();
        course.setId(courseId);
        course.setPrice(0.0);

        when(courseRepo.findById(courseId)).thenReturn(Optional.of(course));
        when(enrollRepo.existsByStudentIdAndCourseId(studentId, courseId)).thenReturn(false);

        Enrollment saved = new Enrollment();
        UUID enrollmentId = UUID.randomUUID();
        saved.setId(enrollmentId);
        when(enrollRepo.save(any(Enrollment.class))).thenReturn(saved);

        UUID result = cbEnrollmentService.enroll(student, courseId);

        assertEquals(enrollmentId, result);
        verify(enrollRepo).save(any(Enrollment.class));
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void testEnroll_PaidCourse_Success_TriggerEvent() {
        User student = mock(User.class);
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        when(student.getId()).thenReturn(studentId);

        Course course = new Course();
        course.setId(courseId);
        course.setPrice(10000.0);

        when(courseRepo.findById(courseId)).thenReturn(Optional.of(course));
        when(enrollRepo.existsByStudentIdAndCourseId(studentId, courseId)).thenReturn(false);

        Enrollment saved = new Enrollment();
        UUID enrollmentId = UUID.randomUUID();
        saved.setId(enrollmentId);
        when(enrollRepo.save(any(Enrollment.class))).thenReturn(saved);

        UUID result = cbEnrollmentService.enroll(student, courseId);

        assertEquals(enrollmentId, result);
        verify(enrollRepo).save(any(Enrollment.class));
        verify(eventPublisher).publishEvent(any());
    }

    @Test
    void testEnroll_AlreadyEnrolled_ThrowsException() {
        User student = mock(User.class);
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        when(student.getId()).thenReturn(studentId);

        when(courseRepo.findById(courseId)).thenReturn(Optional.of(new Course()));
        when(enrollRepo.existsByStudentIdAndCourseId(studentId, courseId)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> cbEnrollmentService.enroll(student, courseId));
    }

    @Test
    void testMyCourses_ReturnsPaidAndFreeCourses() {
        User student = mock(User.class);
        UUID studentId = UUID.randomUUID();
        when(student.getId()).thenReturn(studentId);

        Course freeCourse = new Course();
        freeCourse.setId(UUID.randomUUID());
        freeCourse.setName("Free");
        freeCourse.setPrice(0.0);

        Course paidCourse = new Course();
        paidCourse.setId(UUID.randomUUID());
        paidCourse.setName("Paid");
        paidCourse.setPrice(10000.0);

        Enrollment paidEnrollment = new Enrollment();
        paidEnrollment.setId(UUID.randomUUID());
        paidEnrollment.setCourse(paidCourse);
        paidEnrollment.setEnrollmentDate(LocalDateTime.now());
        paidEnrollment.setPaymentStatus(Enrollment.PaymentStatus.PAID);

        Enrollment freeEnrollment = new Enrollment();
        freeEnrollment.setId(UUID.randomUUID());
        freeEnrollment.setCourse(freeCourse);
        freeEnrollment.setEnrollmentDate(LocalDateTime.now());
        freeEnrollment.setPaymentStatus(Enrollment.PaymentStatus.PENDING);

        List<Enrollment> enrollments = Arrays.asList(paidEnrollment, freeEnrollment);

        when(enrollRepo.findByStudentId(studentId)).thenReturn(enrollments);

        List<EnrollmentDto> result = cbEnrollmentService.myCourses(student);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(e -> e.courseName().equals("Free")));
        assertTrue(result.stream().anyMatch(e -> e.courseName().equals("Paid")));
    }
}
