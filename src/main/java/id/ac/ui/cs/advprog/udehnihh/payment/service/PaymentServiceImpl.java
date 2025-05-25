package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
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