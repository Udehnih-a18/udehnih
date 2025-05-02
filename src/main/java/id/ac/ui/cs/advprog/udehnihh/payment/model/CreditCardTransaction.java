package id.ac.ui.cs.advprog.udehnihh.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreditCardTransaction extends Transaction {
    private String cvc;
}
