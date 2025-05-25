package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.BankTransfer;
import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCard;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.repository.TransactionRepository;
import id.ac.ui.cs.advprog.udehnihh.payment.strategy.PaymentStrategy;
import id.ac.ui.cs.advprog.udehnihh.payment.factory.PaymentStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentStrategyFactory strategyFactory;

    @Autowired
    public PaymentServiceImpl(PaymentStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public List<Transaction> getTransactionByStudent(User student) {
        return student.getTransactionList();
    }

    @Override
    public Transaction getTransactionById(UUID transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }

    @Override
    public void updateTransactionStatus(UUID transactionId, TransactionStatus status) {
        transactionRepository.findByTransactionId(transactionId).setStatus(status);
    }

    @Override
    public void cancelTransaction(UUID transactionId) {
        transactionRepository.findByTransactionId(transactionId).setStatus(TransactionStatus.CANCELLED);
    }
}

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public BankTransfer createBankTransfer(Course course, User student, AvailableBanks bank) {
        BankTransfer transfer = new BankTransfer(course, student, bank);
        return (BankTransfer) transactionRepository.save(transfer);
    }

    public CreditCard createCreditCard(Course course, User student, String accountNumber, String cvc) {
        // No external processing - just store the data
        CreditCard card = new CreditCard(course, student, accountNumber, cvc);
        // You might set status to COMPLETED immediately or keep as PENDING
        card.setStatus(TransactionStatus.PAID);
        return (CreditCard) transactionRepository.save(card);
    }

    public void markBankTransferAsCompleted(UUID transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        if (transaction instanceof BankTransfer bankTransfer) {
            bankTransfer.setAlreadyTransferred(true);
            bankTransfer.setStatus(TransactionStatus.PAID);
            transactionRepository.save(bankTransfer);
        } else {
            throw new IllegalArgumentException("Transaction is not a bank transfer");
        }
    }
}