package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import static org.junit.jupiter.api.Assertions.assertEquals;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.RefundStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class RefundTest {
    @Test
    public void constructorTest() {
        User student = new User();
        User tutor = new User();

        Course course = new Course();
        course.setPrice(BigDecimal.TEN);
        course.setTutor(tutor);
        course.setName("Restauratn");

        BankTransfer transaction = new BankTransfer(UUID.randomUUID(), course, student, AvailableBanks.BANK_SENDIRI);
        Refund refund = new Refund(transaction, "salah coursee :(");

        assertEquals(refund.getTransaction().getTransactionId(), transaction.getTransactionId());
        assertEquals(refund.getReasonForRefund(), "salah coursee :(");
        assertEquals(refund.getStatus(), RefundStatus.ON_HOLD);
    }
}
