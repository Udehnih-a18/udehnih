package id.ac.ui.cs.advprog.udehnihh.payment.strategy;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(Transaction transaction) {
        CreditCardTransaction ccTransaction = (CreditCardTransaction) transaction;

        if (ccTransaction.getCvc().length() < 3)
            throw new IllegalArgumentException("Invalid CVC");

        transaction.setStatus(TransactionStatus.PENDING);
    }

    @Override
    public Transaction createCreditCardTransaction(Transaction tx) {
        Optional<User> tutorOpt = transactionRepository.findById();
        if (tutorOpt.isEmpty()) {
            throw new IllegalArgumentException("Tutor not found");
        }

        Transaction transaction = new Transaction();
        PaymentStrategy strategy = strategyFactory.getStrategy(tx.getMethod());
        strategy.pay(tx);
        return transactionRepository.create(tx);
    }
}

