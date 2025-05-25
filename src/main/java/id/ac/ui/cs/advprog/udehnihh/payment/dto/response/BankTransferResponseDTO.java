package id.ac.ui.cs.advprog.udehnihh.payment.dto.response;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import lombok.Getter;
import lombok.Setter;

// Bank Transfer specific response
@Getter
@Setter
public class BankTransferResponseDTO extends TransactionResponseDTO {
    private AvailableBanks bank;
    private String bankAccountNumber; // From enum
    private boolean alreadyTransferred;
}
