package id.ac.ui.cs.advprog.udehnihh.payment.strategy;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankTransferPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(Transaction transaction) {
        // Simpan status PENDING, tunggu konfirmasi manual
        transaction.setStatus(TransactionStatus.PENDING);
    }

    public Transaction createBankTransferTransaction(Transaction tx);
    public void updateAlreadyTransferred(UUID transactionId);

}
