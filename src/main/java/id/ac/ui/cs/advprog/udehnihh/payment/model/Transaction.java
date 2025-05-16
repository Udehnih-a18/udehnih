package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Transaction {
    private UUID id = UUID.randomUUID();

    private String courseName;
    private String tutorName;

    private double price;

    private PaymentMethod method;
    private String accountNumber;

    private TransactionStatus status;

    private User student;

    private LocalDateTime createdAt;

    public Transaction(Course course, User student, PaymentMethod method, String accountNumber) {
        this.courseName = course.getName();
        this.tutorName = course.getTutor().getFullName();
        this.price = course.getPrice();
        this.student = student;
        this.method = method;
        this.accountNumber = accountNumber;
        this.status = TransactionStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public Transaction() {
        this.status = TransactionStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }
}
