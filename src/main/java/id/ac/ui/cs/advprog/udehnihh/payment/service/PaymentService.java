package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PaymentService {
    public Transaction createTransaction();
    public void confirmTransfer(UUID transactionId);
    public List<Transaction> getTransactionHistory(UUID studentId);
    public void cancelTransaction(UUID transactionId);
    public void requestRefund(UUID transactionId);
    public void updateTransactionStatus(UUID transactionId, TransactionStatus status);

}
