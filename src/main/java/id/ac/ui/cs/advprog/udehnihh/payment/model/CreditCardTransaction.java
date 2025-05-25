package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "credit_card_transaction")
public class CreditCardTransaction extends Transaction {

    @Column(name = "cvc", nullable = false)
    private String cvc;

    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    public CreditCardTransaction() {
        super();
        this.setMethod(PaymentMethod.CREDIT_CARD);
    }

    public CreditCardTransaction(Course course, User student, PaymentMethod method, String accountNumber, String cvc) {
        super(course, student, method);
        this.cvc = cvc;
        this.accountNumber = accountNumber;
    }
}
