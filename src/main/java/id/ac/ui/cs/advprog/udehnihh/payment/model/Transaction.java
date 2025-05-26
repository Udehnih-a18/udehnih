package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

// Base Transaction class
@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "payment_method", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;

    @Column(name = "course_id", nullable = false)
    private UUID courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "tutor_name", nullable = false)
    private String tutorName;

    @Column(name = "price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private Refund refund;

    // Constructor for common initialization
    public Transaction() {
        this.status = TransactionStatus.PENDING;
    }

    public Transaction(Course course, User student) {
        this();
        this.courseId = course.getId();
        this.courseName = course.getName();
        this.tutorName = course.getTutor().getFullName();
        this.price = course.getPrice();
        this.student = student;
    }

    // Abstract method to get payment method
    public abstract PaymentMethod getPaymentMethod();
}

