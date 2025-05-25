package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class BankTransferTransaction extends Transaction {

    @Column(name = "bank", nullable = false)
    private AvailableBanks bank;

    @Column(name = "already_transferred", nullable = false)
    private boolean alreadyTransferred = false;

    public BankTransferTransaction() {
        super();
        this.setMethod(PaymentMethod.BANK_TRANSFER);
    }

    public BankTransferTransaction(Course course, User student, PaymentMethod method, AvailableBanks bank) {
        super(course, student, method);
        this.bank = bank;
        this.alreadyTransferred = false;
    }
}
