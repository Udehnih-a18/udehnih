package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @ManyToOne
    private User student;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private Refund refund;

    public Transaction(Course course, User student, PaymentMethod method) {
        this.courseName = course.getName();
        this.tutorName = course.getTutor().getFullName();
        this.price = course.getPrice();
        this.student = student;
        this.method = method;
        this.status = TransactionStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public Transaction() {
        this.status = TransactionStatus.PENDING;
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
}
