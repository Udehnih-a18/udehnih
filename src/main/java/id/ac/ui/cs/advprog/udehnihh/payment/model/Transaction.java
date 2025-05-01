package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Transaction {
    private String id = UUID.randomUUID().toString();

    private String courseName;
    private String tutorName;

    private double price;

    private PaymentMethod method;

    private TransactionStatus status;

    private Student student;

    private LocalDateTime createdAt = LocalDateTime.now();
}
