package id.ac.ui.cs.advprog.udehnihh.payment.strategy;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Component
public class BankTransferPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(Transaction transaction) {

    }
}
