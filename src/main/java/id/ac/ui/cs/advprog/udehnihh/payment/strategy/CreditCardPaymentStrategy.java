package id.ac.ui.cs.advprog.udehnihh.payment.strategy;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCard;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(Transaction transaction) {
        CreditCard ccTransaction = (CreditCard) transaction;

        if (ccTransaction.getCvc().length() < 3)
            throw new IllegalArgumentException("Invalid CVC");

        transaction.setStatus(TransactionStatus.PENDING);
    }

}

