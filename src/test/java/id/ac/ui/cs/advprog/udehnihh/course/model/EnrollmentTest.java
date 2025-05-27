package id.ac.ui.cs.advprog.udehnihh.course.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {

    @Test
    void testEnrollmentSettersAndGetters() {
        UUID enrollmentId = UUID.randomUUID();
        User student = new User();
        student.setId(UUID.randomUUID());
        Course course = new Course();
        course.setId(UUID.randomUUID());
        LocalDateTime enrollmentDate = LocalDateTime.now();
        Enrollment.PaymentStatus paymentStatus = Enrollment.PaymentStatus.PAID;

        Enrollment enrollment = new Enrollment();

        enrollment.setId(enrollmentId);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(enrollmentDate);
        enrollment.setPaymentStatus(paymentStatus);

        assertEquals(enrollmentId, enrollment.getId());
        assertEquals(student, enrollment.getStudent());
        assertEquals(course, enrollment.getCourse());
        assertEquals(enrollmentDate, enrollment.getEnrollmentDate());
        assertEquals(paymentStatus, enrollment.getPaymentStatus());
    }

    @Test
    void testDefaultConstructor() {
        Enrollment enrollment = new Enrollment();

        assertNull(enrollment.getId());
        assertNull(enrollment.getStudent());
        assertNull(enrollment.getCourse());
        assertNull(enrollment.getEnrollmentDate());
        assertNull(enrollment.getPaymentStatus());
    }

    @Test
    void testAllArgsConstructor() {
        UUID enrollmentId = UUID.randomUUID();
        User student = new User();
        student.setId(UUID.randomUUID());
        Course course = new Course();
        course.setId(UUID.randomUUID());
        LocalDateTime enrollmentDate = LocalDateTime.now();
        Enrollment.PaymentStatus paymentStatus = Enrollment.PaymentStatus.PENDING;

        Enrollment enrollment = new Enrollment(enrollmentId, student, course, enrollmentDate, paymentStatus);

        assertEquals(enrollmentId, enrollment.getId());
        assertEquals(student, enrollment.getStudent());
        assertEquals(course, enrollment.getCourse());
        assertEquals(enrollmentDate, enrollment.getEnrollmentDate());
        assertEquals(paymentStatus, enrollment.getPaymentStatus());
    }

    @Test
    void testPaymentStatusEnum() {
        assertEquals("PENDING", Enrollment.PaymentStatus.PENDING.name());
        assertEquals("PAID", Enrollment.PaymentStatus.PAID.name());
        assertEquals("FAILED", Enrollment.PaymentStatus.FAILED.name());
    }
}
