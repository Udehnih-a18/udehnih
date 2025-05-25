package id.ac.ui.cs.advprog.udehnihh.payment.dto;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

// List/Summary DTO
@Getter
@Setter
public class TransactionSummaryDTO {
    private UUID id;
    private String courseName;
    private double price;
    private PaymentMethod method;
    private TransactionStatus status;
    private LocalDateTime createdAt;
}
