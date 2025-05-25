package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BankTransferTransactionTest {
    @Test
    public void constructorTest() {
        User student = new User();
        User tutor = new User();

        Course course = new Course();
        course.setName("Restauratn");
        course.setPrice(5.0);
        course.setTutor(tutor);
        tutor.setFullName("Robin Smith");

        BankTransferTransaction bankTransferTransaction = new BankTransferTransaction(course, student, PaymentMethod.BANK_TRANSFER, AvailableBanks.AMPUN_BANK_JAGO);

        assertEquals(bankTransferTransaction.getCourseName(), course.getName());
        assertEquals(bankTransferTransaction.getTutorName(), course.getTutor().getFullName());
        assertEquals(bankTransferTransaction.getPrice(), course.getPrice());

        assertEquals(bankTransferTransaction.getStatus(), TransactionStatus.PENDING);
        assertEquals(bankTransferTransaction.getBank(), AvailableBanks.AMPUN_BANK_JAGO);
        assertEquals(bankTransferTransaction.getMethod(), PaymentMethod.BANK_TRANSFER);
        assertFalse(bankTransferTransaction.isAlreadyTransferred());
    }
}
