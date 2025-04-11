@Service
public interface PaymentService {
    public Transaction createTransaction(Transaction tx, PaymentRequest request)
    public void confirmTransfer(UUID transactionId);
    public List<Transaction> getTransactionHistory(UUID studentId);
    public void cancelTransaction(UUID transactionId);
    public void requestRefund(UUID transactionId);
    public void updateTransactionStatus(UUID transactionId, TransactionStatus status);

}
