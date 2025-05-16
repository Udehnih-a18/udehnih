package id.ac.ui.cs.advprog.udehnihh.payment.strategy;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCardTransaction;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(Transaction transaction) {
        CreditCardTransaction ccTransaction = (CreditCardTransaction) transaction;

        if (ccTransaction.getCvc().length() < 3)
            throw new IllegalArgumentException("Invalid CVC");

        transaction.setStatus(TransactionStatus.PENDING);
    }
}

