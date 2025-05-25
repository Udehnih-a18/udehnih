package id.ac.ui.cs.advprog.udehnihh.payment.dto;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

// Base Response DTO
@Getter
@Setter
public class TransactionResponseDTO {
    private UUID id;
    private UUID courseId;
    private String courseName;
    private String tutorName;
    private double price;
    private PaymentMethod method;
    private TransactionStatus status;
    private String studentName;
    private LocalDateTime createdAt;
    private boolean hasRefund;
}

