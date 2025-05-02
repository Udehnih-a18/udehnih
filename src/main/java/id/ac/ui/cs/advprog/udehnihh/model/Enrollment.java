package id.ac.ui.cs.advprog.udehnihh.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

import java.time.LocalDateTime;

public class Enrollment {
    private Long id;
    private User student;
    private Course course;
    private LocalDateTime enrollmentDate;
    private String paymentStatus; // PENDING, PAID, FAILED

    public Enrollment() {
    }

    // Constructor
    public Enrollment(Long id, User student, Course course, LocalDateTime enrollmentDate, String paymentStatus) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.paymentStatus = paymentStatus;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public User getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}