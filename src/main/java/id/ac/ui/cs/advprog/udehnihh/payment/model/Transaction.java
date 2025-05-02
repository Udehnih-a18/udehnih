package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
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

    private LocalDateTime createdAt = LocalDateTime.now();

    public Transaction(Course course, PaymentMethod method, User student) {
        this.courseName = course.getName;
        this.courseTutorName = course.getTutorName;
        this.coursePrice = course.getPrice;
    }
}
