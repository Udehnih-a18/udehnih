package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.course.dto.EnrollmentDto;
import id.ac.ui.cs.advprog.udehnihh.course.repository.*;
import id.ac.ui.cs.advprog.udehnihh.course.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CbEnrollmentService {

    private final CbEnrollmentRepository enrollRepo;
    private final CbCourseRepository courseRepo;
    private final ApplicationEventPublisher eventPublisher;

    @PreAuthorize("hasRole('STUDENT')")
    public UUID enroll(User student, UUID courseId) {
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // sudah enrol?
        boolean exists = enrollRepo.existsByStudentIdAndCourseId(student.getId(), courseId);
        if (exists) throw new IllegalStateException("Already enrolled");

        Enrollment e = new Enrollment();
        e.setStudent(student);
        e.setCourse(course);
        e.setEnrollmentDate(LocalDateTime.now());

        if (course.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            e.setPaymentStatus(Enrollment.PaymentStatus.PAID);
        } else {
            // trigger payment workflow; PENDING sampai status PAID
            e.setPaymentStatus(Enrollment.PaymentStatus.PENDING);
            publishPaymentPendingEventAsync(e);
        }

        return enrollRepo.save(e).getId();
    }

    @Async
    public void publishPaymentPendingEventAsync(Enrollment e) {
        eventPublisher.publishEvent(new EnrollmentPaymentPendingEvent(this, e));
    }

    @PreAuthorize("hasRole('STUDENT')")
    public List<EnrollmentDto> myCourses(User student) {
        return enrollRepo.findByStudentId(student.getId()).stream()
                .map(e -> new EnrollmentDto(
                        e.getId(),
                        e.getCourse().getId(),
                        e.getCourse().getName(),
                        e.getEnrollmentDate(),
                        e.getPaymentStatus().name()))
                .toList();
    }
}

class EnrollmentPaymentPendingEvent {
    private final Object source;
    private final Enrollment enrollment;

    public EnrollmentPaymentPendingEvent(Object source, Enrollment enrollment) {
        this.source = source;
        this.enrollment = enrollment;
    }

    public Object getSource() {
        return source;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }
}
