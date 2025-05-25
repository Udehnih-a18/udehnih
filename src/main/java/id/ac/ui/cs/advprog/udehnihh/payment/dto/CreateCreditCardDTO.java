package id.ac.ui.cs.advprog.udehnihh.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateCreditCardDTO {
    @NotNull
    private UUID courseId;

    @NotBlank
    @Pattern(regexp = "\\d{16}", message = "Account number must be 16 digits")
    private String accountNumber;

    @NotBlank
    @Pattern(regexp = "\\d{3}", message = "CVC must be 3 digits")
    private String cvc;
}
