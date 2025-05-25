package id.ac.ui.cs.advprog.udehnihh.payment.dto.request;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import jakarta.validation.constraints.NotBlank;
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

    @NotNull
    private boolean alreadyTransferred;
}
