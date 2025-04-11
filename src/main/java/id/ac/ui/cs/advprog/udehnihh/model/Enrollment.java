package id.ac.ui.cs.advprog.udehnihh.model;

import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

public class Enrollment {
    private Long id;
    private User student;
    private Course course;
    private LocalDateTime enrollmentDate;
    private String paymentStatus; // PENDING, PAID, FAILED

    // Getters, setters, constructors
}