package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CreditCardTransaction extends Transaction {
    private String cvc;

    public CreditCardTransaction() {
        super();
        this.setMethod(PaymentMethod.CREDIT_CARD);
    }

    public CreditCardTransaction(Course course, User student, PaymentMethod method, String accountNumber, String cvc) {
        super(course, student, method, accountNumber);
        this.cvc = cvc;
    }
}
