package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(Transaction transaction, PaymentRequest request) {
        CreditCardPaymentRequest ccRequest = (CreditCardPaymentRequest) request;

        if (ccRequest.getCardNumber().length() < 13)
            throw new IllegalArgumentException("Invalid card number");
        if (ccRequest.getCvc().length() < 3)
            throw new IllegalArgumentException("Invalid CVC");

        transaction.setStatus(TransactionStatus.PENDING);
    }
}

