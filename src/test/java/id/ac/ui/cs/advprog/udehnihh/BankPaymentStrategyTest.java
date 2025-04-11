class BankTransferPaymentStrategyTest {

    private BankTransferPaymentStrategy strategy;

    @BeforeEach
    void setup() {
        strategy = new BankTransferPaymentStrategy();
    }

    @Test
    void shouldSetTransactionStatusToPending() {
        Transaction transaction = new Transaction();
        transaction.setMethod(PaymentMethod.BANK_TRANSFER);

        PaymentRequest request = new PaymentRequest();

        strategy.pay(transaction, request);

        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
    }
}
