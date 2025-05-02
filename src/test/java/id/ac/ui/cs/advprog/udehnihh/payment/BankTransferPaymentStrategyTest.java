package id.ac.ui.cs.advprog.udehnihh.service;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.strategy.BankTransferPaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankTransferPaymentStrategyTest {

    private BankTransferPaymentStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new BankTransferPaymentStrategy();
    }

    @Test
    void shouldSetTransactionStatusToPending() {
        Transaction transaction = new Transaction();
        transaction.setMethod(PaymentMethod.BANK_TRANSFER);

        strategy.pay(transaction);

        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
    }
}
