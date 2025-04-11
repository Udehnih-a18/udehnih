@Service
public class BankTransferPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(Transaction transaction, PaymentRequest request) {
        // Simpan status PENDING, tunggu konfirmasi manual
        transaction.setStatus(TransactionStatus.PENDING);
    }
}
