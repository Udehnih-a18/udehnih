package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCardTransaction;
import id.ac.ui.cs.advprog.udehnihh.payment.strategy.CreditCardPaymentStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreditCardTransactionPaymentStrategyTest {

    private CreditCardPaymentStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new CreditCardPaymentStrategy();
    }

    @Test
    void shouldSetStatusToPendingIfCardValid() {
        CreditCardTransaction transaction = new CreditCardTransaction();
        transaction.setMethod(PaymentMethod.CREDIT_CARD);

        transaction.setCvc("123");

        strategy.pay(transaction);

        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
    }

    @Test
    void shouldThrowExceptionIfCardNumberInvalid() {
        CreditCardTransaction transaction = new CreditCardTransaction();
        transaction.setMethod(PaymentMethod.CREDIT_CARD);

        transaction.setCvc("123");

        assertThrows(IllegalArgumentException.class, () ->
                strategy.pay(transaction));
    }

    @Test
    void shouldThrowExceptionIfCvcInvalid() {
        CreditCardTransaction transaction = new CreditCardTransaction();
        transaction.setMethod(PaymentMethod.CREDIT_CARD);

        transaction.setCvc("1"); // invalid

        assertThrows(IllegalArgumentException.class, () ->
                strategy.pay(transaction));
    }
}
