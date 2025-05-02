package id.ac.ui.cs.advprog.udehnihh.payment.strategy;

import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;

public interface PaymentStrategy {
    void pay(Transaction transaction);
}
