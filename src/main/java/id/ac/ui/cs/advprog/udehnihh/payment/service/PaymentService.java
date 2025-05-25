package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PaymentService {
    public List<Transaction> getTransactionByStudent(User student);

    Transaction getTransactionById(UUID transactionId);

    public void updateTransactionStatus(UUID transactionId, TransactionStatus status);
    public void cancelTransaction(UUID transactionId);
}
