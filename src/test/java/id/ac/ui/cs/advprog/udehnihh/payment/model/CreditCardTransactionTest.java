package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreditCardTransactionTest {
    @Test
    public void constructorTest() {
        Course course = new Course();
        User user = new User();
        CreditCardTransaction bankTransferTransaction = new CreditCardTransaction(course, user, PaymentMethod.BANK_TRANSFER, "1234567890", "123");

        assertEquals(bankTransferTransaction.getCourseName(), course.getName());
        assertEquals(bankTransferTransaction.getTutorName(), course.getTutor().getName());
        assertEquals(bankTransferTransaction.getPrice(), course.getPrice());
        assertEquals(bankTransferTransaction.getAccountNumber(), "1234567890");

        assertEquals(bankTransferTransaction.getStatus(), TransactionStatus.PENDING);
        assertEquals(bankTransferTransaction.getMethod(), PaymentMethod.CREDIT_CARD);
    }
}
