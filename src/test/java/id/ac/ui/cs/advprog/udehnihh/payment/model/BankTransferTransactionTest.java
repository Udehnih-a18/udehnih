package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BankTransferTransactionTest {
    @Test
    public void constructorTest() {
        Course course = new Course();
        User user = new User();
        BankTransferTransaction bankTransferTransaction = new BankTransferTransaction(course, user, PaymentMethod.BANK_TRANSFER, "1234567890", AvailableBanks.AMPUN_BANK_JAGO);

        assertEquals(bankTransferTransaction.getCourseName(), course.getName());
        assertEquals(bankTransferTransaction.getTutorName(), course.getTutor().getName());
        assertEquals(bankTransferTransaction.getPrice(), course.getPrice());
        assertEquals(bankTransferTransaction.getAccountNumber(), "1234567890");

        assertEquals(bankTransferTransaction.getStatus(), TransactionStatus.PENDING);
        assertEquals(bankTransferTransaction.getBank(), AvailableBanks.AMPUN_BANK_JAGO);
        assertEquals(bankTransferTransaction.getMethod(), PaymentMethod.BANK_TRANSFER);
        assertFalse(bankTransferTransaction.isAlreadyTransferred());
    }
}
