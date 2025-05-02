package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class BankTransferPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(Transaction transaction) {
        // Simpan status PENDING, tunggu konfirmasi manual
        transaction.setStatus(TransactionStatus.PENDING);
    }
}
