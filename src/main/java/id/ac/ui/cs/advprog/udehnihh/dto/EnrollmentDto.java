package id.ac.ui.cs.advprog.udehnihh.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record EnrollmentDto(
        UUID enrollmentId,
        UUID courseId,
        String courseName,
        LocalDateTime enrolledAt,
        String paymentStatus
) {}
