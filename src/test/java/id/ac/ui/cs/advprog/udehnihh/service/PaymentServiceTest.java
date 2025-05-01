import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private PaymentStrategyFactory strategyFactory;

    @Mock
    private BankTransferPaymentStrategy bankStrategy;

    @Mock
    private CreditCardPaymentStrategy creditStrategy;

    @InjectMocks
    private PaymentService paymentService;

    private Transaction dummyTransaction;

    @BeforeEach
    void setup() {
        dummyTransaction = new Transaction();
        dummyTransaction.setId(UUID.randomUUID());
        dummyTransaction.setCourseName("Spring Boot Mastery");
        dummyTransaction.setTutorName("Jane Doe");
        dummyTransaction.setPrice(BigDecimal.valueOf(250000));
        dummyTransaction.setStudent(new Student(UUID.randomUUID(), "Budi"));
    }

    @Test
    void shouldCreateBankTransferTransactionWithPendingStatus() {
        dummyTransaction.setMethod(PaymentMethod.BANK_TRANSFER);

        when(strategyFactory.getStrategy(PaymentMethod.BANK_TRANSFER)).thenReturn(bankStrategy);
        doAnswer(inv -> {
            dummyTransaction.setStatus(TransactionStatus.PENDING);
            return null;
        }).when(bankStrategy).pay(eq(dummyTransaction), any());

        when(transactionRepository.save(any())).thenReturn(dummyTransaction);

        PaymentRequest request = new PaymentRequest();
        request.setMethod(PaymentMethod.BANK_TRANSFER);

        Transaction result = paymentService.createTransaction(dummyTransaction, request);

        assertEquals(TransactionStatus.PENDING, result.getStatus());
        verify(transactionRepository).save(dummyTransaction);
    }

    @Test
    void shouldCreateCreditCardTransactionWithPendingStatus() {
        dummyTransaction.setMethod(PaymentMethod.CREDIT_CARD);

        when(strategyFactory.getStrategy(PaymentMethod.CREDIT_CARD)).thenReturn(creditStrategy);
        doAnswer(inv -> {
            dummyTransaction.setStatus(TransactionStatus.PENDING);
            return null;
        }).when(creditStrategy).pay(eq(dummyTransaction), any());

        when(transactionRepository.save(any())).thenReturn(dummyTransaction);

        CreditCardPaymentRequest request = new CreditCardPaymentRequest();
        request.setMethod(PaymentMethod.CREDIT_CARD);
        request.setCardNumber("1234567890123456");
        request.setCvc("123");

        Transaction result = paymentService.createTransaction(dummyTransaction, request);

        assertEquals(TransactionStatus.PENDING, result.getStatus());
        verify(transactionRepository).save(dummyTransaction);
    }

    @Test
    void shouldThrowExceptionWhenCreditCardNumberInvalid() {
        dummyTransaction.setMethod(PaymentMethod.CREDIT_CARD);

        when(strategyFactory.getStrategy(PaymentMethod.CREDIT_CARD)).thenReturn(creditStrategy);
        doThrow(new IllegalArgumentException("Invalid card number"))
                .when(creditStrategy).pay(eq(dummyTransaction), any());

        CreditCardPaymentRequest request = new CreditCardPaymentRequest();
        request.setMethod(PaymentMethod.CREDIT_CARD);
        request.setCardNumber("123"); // invalid
        request.setCvc("123");

        assertThrows(IllegalArgumentException.class, () ->
                paymentService.createTransaction(dummyTransaction, request));
    }
}
