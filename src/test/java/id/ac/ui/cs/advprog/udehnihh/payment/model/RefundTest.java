package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import static org.junit.jupiter.api.Assertions.assertEquals;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.RefundStatus;
import org.junit.jupiter.api.Test;

public class RefundTest {
    @Test
    public void constructorTest() {
        Course course = new Course();
        User user = new User();
        Transaction transaction = new Transaction(course, user, PaymentMethod.BANK_TRANSFER, "1234567890");
        Refund refund = new Refund(transaction, "salah coursee :(");

        assertEquals(refund.getTransaction(), transaction);
        assertEquals(refund.getReasonForRefund(), "salah coursee :(");
        assertEquals(refund.getStatus(), RefundStatus.ON_HOLD);
    }
}
