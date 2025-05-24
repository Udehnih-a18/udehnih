package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "tutor_name", nullable = false)
    private String tutorName;

    @Column(name = "price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)

    private PaymentMethod method;


    private String accountNumber;

    private TransactionStatus status;

    @ManyToOne
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
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}
