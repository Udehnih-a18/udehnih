package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

// Credit Card specific model
@Entity
@DiscriminatorValue("CREDIT_CARD")
@Getter
@Setter
public class CreditCard extends Transaction {

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "masked_account_number")
    private String maskedAccountNumber;

    // Store CVC since there's no external processing
    // But still be careful about exposing it in APIs
    @Column(name = "cvc")
    private String cvc;

    public CreditCard() {
        super();
    }

    public CreditCard(UUID transactionId, Course course, User student, String accountNumber, String cvc) {
        super(transactionId, course, student);
        this.accountNumber = accountNumber;
        this.cvc = cvc;
        this.maskedAccountNumber = maskAccountNumber(accountNumber);
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.CREDIT_CARD;
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        return "****-****-****-" + accountNumber.substring(accountNumber.length() - 4);
    }
}