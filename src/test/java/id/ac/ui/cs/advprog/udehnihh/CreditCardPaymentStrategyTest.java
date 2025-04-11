class CreditCardPaymentStrategyTest {

    private CreditCardPaymentStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new CreditCardPaymentStrategy();
    }

    @Test
    void shouldSetStatusToPendingIfCardValid() {
        Transaction transaction = new Transaction();
        transaction.setMethod(PaymentMethod.CREDIT_CARD);

        CreditCardPaymentRequest request = new CreditCardPaymentRequest();
        request.setCardNumber("4111111111111111");
        request.setCvc("123");

        strategy.pay(transaction, request);

        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
    }

    @Test
    void shouldThrowExceptionIfCardNumberInvalid() {
        Transaction transaction = new Transaction();
        transaction.setMethod(PaymentMethod.CREDIT_CARD);

        CreditCardPaymentRequest request = new CreditCardPaymentRequest();
        request.setCardNumber("12");
        request.setCvc("123");

        assertThrows(IllegalArgumentException.class, () ->
                strategy.pay(transaction, request));
    }

    @Test
    void shouldThrowExceptionIfCvcInvalid() {
        Transaction transaction = new Transaction();
        transaction.setMethod(PaymentMethod.CREDIT_CARD);

        CreditCardPaymentRequest request = new CreditCardPaymentRequest();
        request.setCardNumber("4111111111111111");
        request.setCvc("1"); // invalid

        assertThrows(IllegalArgumentException.class, () ->
                strategy.pay(transaction, request));
    }
}
