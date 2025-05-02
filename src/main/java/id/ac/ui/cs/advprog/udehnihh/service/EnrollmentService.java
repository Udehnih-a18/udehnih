package id.ac.ui.cs.advprog.udehnihh.service;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.exception.AlreadyEnrolledException;
import id.ac.ui.cs.advprog.udehnihh.exception.ResourceNotFoundException;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import id.ac.ui.cs.advprog.udehnihh.model.Enrollment;
import id.ac.ui.cs.advprog.udehnihh.observer.EnrollmentObserver;
import id.ac.ui.cs.advprog.udehnihh.repository.CourseCreationRepository;
import id.ac.ui.cs.advprog.udehnihh.repository.EnrollmentRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseCreationRepository courseRepository;
    private final List<EnrollmentObserver> observers = new ArrayList<>();

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseCreationRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    public void addObserver(EnrollmentObserver observer) {
        observers.add(observer);
    }

    public Enrollment enrollStudentToCourse(User student, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        // Check if student is already enrolled
        if (enrollmentRepository.findByStudentAndCourse(student, course).isPresent()) {
            throw new AlreadyEnrolledException("Student already enrolled to this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        // Set payment status based on course price
        if (course.getPrice() <= 0) {
            enrollment.setPaymentStatus("PAID");
        } else {
            enrollment.setPaymentStatus("PENDING");
        }

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // Notify observers
        for (EnrollmentObserver observer : observers) {
            observer.onEnrollment(savedEnrollment);
        }

        return savedEnrollment;
    }

    public List<Enrollment> getEnrollmentsByStudent(User student) {
        return enrollmentRepository.findByStudent(student);
    }
}