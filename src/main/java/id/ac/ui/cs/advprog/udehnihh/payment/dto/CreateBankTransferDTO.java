package id.ac.ui.cs.advprog.udehnihh.payment.dto;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

// Request DTOs
@Getter
@Setter
public class CreateBankTransferDTO {
    @NotNull
    private UUID courseId;

    @NotNull
    private AvailableBanks bank;
}
