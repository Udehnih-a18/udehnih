package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Bank Transfer specific model
@Entity
@DiscriminatorValue("BANK_TRANSFER")
@Getter
@Setter
public class BankTransfer extends Transaction {

    @Enumerated(EnumType.STRING)
    @Column(name = "bank")
    private AvailableBanks bank;

    public BankTransfer() {
        super();
    }

    public BankTransfer(Course course, User student, AvailableBanks bank) {
        super(course, student);
        this.bank = bank;
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.BANK_TRANSFER;
    }

    public String getBankAccountNumber() {
        return bank != null ? bank.getAccountNumber() : null;
    }
}
