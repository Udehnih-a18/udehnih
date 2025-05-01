package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;

public interface PaymentStrategy {
    void pay(Transaction transaction, PaymentRequest request);
}
