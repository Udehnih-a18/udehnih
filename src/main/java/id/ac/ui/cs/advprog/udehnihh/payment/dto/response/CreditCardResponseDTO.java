package id.ac.ui.cs.advprog.udehnihh.payment.dto.response;

import lombok.Getter;
import lombok.Setter;

// Credit Card specific response (NO CVC!)
@Getter
@Setter
public class CreditCardResponseDTO extends TransactionResponseDTO {
    private String maskedAccountNumber; // "****-****-****-1234"
    // CVC is intentionally NOT included for security
}
