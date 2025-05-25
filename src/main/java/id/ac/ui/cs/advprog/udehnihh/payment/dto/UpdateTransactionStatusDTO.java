package id.ac.ui.cs.advprog.udehnihh.payment.dto;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

// Update DTOs
@Getter
@Setter
public class UpdateTransactionStatusDTO {
    @NotNull
    private TransactionStatus status;
}
