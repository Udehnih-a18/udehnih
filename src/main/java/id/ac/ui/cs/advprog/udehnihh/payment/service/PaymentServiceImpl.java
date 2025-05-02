package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    public PaymentServiceImpl(TransactionRepository repo) {
        this.transactionRepository = transactionRepository;
    }

    @Autowired
    private PaymentStrategyFactory strategyFactory;

    @Override
    public Transaction createTransaction(Transaction tx) {
        PaymentStrategy strategy = strategyFactory.getStrategy(tx.getMethod());
        strategy.pay(tx);
        return transactionRepository.save(tx);
    }

    @Override
    public List<Transaction> getTransactionHistory(UUID studentId) {
        Iterator<Transaction> transactionIterator = transactionRepository.findAllTransactions();
        List<Transaction> allTransactions = new ArrayList<Transaction>();
        transactionIterator.forEachRemaining(allTransactions::add);
        return allTransactions;
    }

    @Override
    public Transaction findById(UUID transactionId) {
        return transactionRepository.findTransactionById();
    }

    @Override
    public void updateTransactionStatus(UUID transactionId, TransactionStatus status) {

    }

    @Override
    public void cancelTransaction(UUID transactionId) {

    }

    @Override
    public void requestRefund(UUID transactionId) {

    }
}