package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.repository.TransactionRepository;
import id.ac.ui.cs.advprog.udehnihh.payment.strategy.BankTransferPaymentStrategy;
import id.ac.ui.cs.advprog.udehnihh.payment.strategy.CreditCardPaymentStrategy;
import id.ac.ui.cs.advprog.udehnihh.payment.factory.PaymentStrategyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);

    @Mock
    private PaymentStrategyFactory strategyFactory;

    @Mock
    private BankTransferPaymentStrategy bankStrategy;

    @Mock
    private CreditCardPaymentStrategy creditStrategy;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Transaction dummyTransaction;

    @BeforeEach
    void setup() {
        dummyTransaction = new Transaction();
        dummyTransaction.setId(UUID.randomUUID());
        dummyTransaction.setCourseName("Spring Boot Mastery");
        dummyTransaction.setTutorName("Jane Doe");
        dummyTransaction.setPrice(250000);
        dummyTransaction.setStudent(new User());
    }

    @Test
    void shouldCreateBankTransferTransactionWithPendingStatus() {
        BankTransferTransaction bankTransferTransaction = new BankTransferTransaction();

        assertEquals(TransactionStatus.PENDING, bankTransferTransaction.getStatus());

    }

    @Test
    void shouldCreateCreditCardTransactionWithPendingStatus() {
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction();

        assertEquals(TransactionStatus.PENDING, creditCardTransaction.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenCvcInvalid() {
        dummyTransaction.setMethod(PaymentMethod.CREDIT_CARD);

        when(strategyFactory.getStrategy(PaymentMethod.CREDIT_CARD)).thenReturn(creditStrategy);
        doThrow(new IllegalArgumentException("Invalid card number"))
                .when(creditStrategy).pay(eq(dummyTransaction));

        CreditCardTransaction request = new CreditCardTransaction();
        request.setMethod(PaymentMethod.CREDIT_CARD);
        request.setCvc("apa");

        assertThrows(IllegalArgumentException.class, () ->
                paymentService.createCreditCardTransaction(dummyTransaction));
    }
}
