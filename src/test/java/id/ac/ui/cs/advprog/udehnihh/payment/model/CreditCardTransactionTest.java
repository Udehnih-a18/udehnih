package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreditCardTransactionTest {
    @Test
    public void constructorTest() {
        User student = new User();
        User tutor = new User();

        Course course = new Course();
        course.setName("Restauratn");
        course.setPrice(5.0);
        course.setTutor(tutor);
        tutor.setFullName("Robin Smith");

        CreditCardTransaction credirCardTransaction = new CreditCardTransaction(course, student, PaymentMethod.CREDIT_CARD, "1234567890", "123");

        assertEquals(credirCardTransaction.getCourseName(), course.getName());
        assertEquals(credirCardTransaction.getTutorName(), course.getTutor().getFullName());
        assertEquals(credirCardTransaction.getPrice(), course.getPrice());
        assertEquals(credirCardTransaction.getAccountNumber(), "1234567890");

        assertEquals(credirCardTransaction.getStatus(), TransactionStatus.PENDING);
        assertEquals(credirCardTransaction.getMethod(), PaymentMethod.CREDIT_CARD);
    }
}
