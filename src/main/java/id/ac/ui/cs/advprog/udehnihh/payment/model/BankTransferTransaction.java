package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankTransferTransaction extends Transaction {
    private AvailableBanks bank;
    private boolean alreadyTransferred;

    public BankTransferTransaction(Course course, User student, PaymentMethod method, String accountNumber, AvailableBanks bank) {
        super(course, student, method, accountNumber);
        this.bank = bank;
        this.alreadyTransferred = false;
    }
}
