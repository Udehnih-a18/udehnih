package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreditCardTransaction extends Transaction {
    private String cvc;

    public CreditCardTransaction(Course course, User student, PaymentMethod method, String accountNumber, String cvc) {
        super(course, student, method, accountNumber);
        this.cvc = cvc;
    }
}
