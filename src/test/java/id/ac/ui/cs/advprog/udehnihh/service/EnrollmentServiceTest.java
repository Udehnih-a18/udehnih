package id.ac.ui.cs.advprog.udehnihh.service;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.udehnihh.observer.EnrollmentObserver;
import id.ac.ui.cs.advprog.udehnihh.repository.CourseCreationRepository;
import id.ac.ui.cs.advprog.udehnihh.repository.EnrollmentRepository;

public class EnrollmentServiceTest {
    private EnrollmentRepository enrollmentRepository;
    private CourseCreationRepository courseRepository;
    private EnrollmentService enrollmentService;
    private EnrollmentObserver observer;

    @BeforeEach
    public void setup() {
        enrollmentRepository = mock(EnrollmentRepository.class);
        courseRepository = mock(CourseCreationRepository.class);
        enrollmentService = new EnrollmentService(enrollmentRepository, courseRepository);
        observer = mock(EnrollmentObserver.class);
        enrollmentService.addObserver(observer);
    }

    @Test
    public void testEnrollStudentToCourse_FreeCourse() {
        // Arrange
        User student = new User(1L, "student@example.com", "Student", "password");
        Course course = new Course(1L, "Free Course", "Learn for free", new User(), 0.0, new ArrayList<>());

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(Optional.empty());

        Enrollment savedEnrollment = new Enrollment();
        savedEnrollment.setId(1L);
        savedEnrollment.setStudent(student);
        savedEnrollment.setCourse(course);
        savedEnrollment.setEnrollmentDate(LocalDateTime.now());
        savedEnrollment.setPaymentStatus("PAID");

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(savedEnrollment);

        // Act
        Enrollment result = enrollmentService.enrollStudentToCourse(student, 1L);

        // Assert
        assertEquals("PAID", result.getPaymentStatus());
        verify(enrollmentRepository).save(any(Enrollment.class));
        verify(observer).onEnrollment(savedEnrollment);
    }

    @Test
    public void testEnrollStudentToCourse_PaidCourse() {
        // Arrange
        User student = new User(1L, "student@example.com", "Student", "password");
        Course course = new Course(1L, "Paid Course", "Premium content", new User(), 50000.0, new ArrayList<>());

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(Optional.empty());

        Enrollment savedEnrollment = new Enrollment();
        savedEnrollment.setId(1L);
        savedEnrollment.setStudent(student);
        savedEnrollment.setCourse(course);
        savedEnrollment.setEnrollmentDate(LocalDateTime.now());
        savedEnrollment.setPaymentStatus("PENDING");

        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(savedEnrollment);

        // Act
        Enrollment result = enrollmentService.enrollStudentToCourse(student, 1L);

        // Assert
        assertEquals("PENDING", result.getPaymentStatus());
        verify(enrollmentRepository).save(any(Enrollment.class));
        verify(observer).onEnrollment(savedEnrollment);
    }

    @Test
    public void testEnrollStudentToCourse_AlreadyEnrolled() {
        // Arrange
        User student = new User(1L, "student@example.com", "Student", "password");
        Course course = new Course(1L, "Java Course", "Learn Java", new User(), 50000.0, new ArrayList<>());

        Enrollment existingEnrollment = new Enrollment();
        existingEnrollment.setStudent(student);
        existingEnrollment.setCourse(course);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(Optional.of(existingEnrollment));

        // Act & Assert
        assertThrows(AlreadyEnrolledException.class, () -> {
            enrollmentService.enrollStudentToCourse(student, 1L);
        });
    }

    @Test
    public void testGetEnrollmentsByStudent() {
        // Arrange
        User student = new User(1L, "student@example.com", "Student", "password");
        List<Enrollment> enrollments = Arrays.asList(
                new Enrollment(1L, student, new Course(), LocalDateTime.now(), "PAID"),
                new Enrollment(2L, student, new Course(), LocalDateTime.now(), "PENDING")
        );

        when(enrollmentRepository.findByStudent(student)).thenReturn(enrollments);

        // Act
        List<Enrollment> result = enrollmentService.getEnrollmentsByStudent(student);

        // Assert
        assertEquals(2, result.size());
        verify(enrollmentRepository).findByStudent(student);
    }
}