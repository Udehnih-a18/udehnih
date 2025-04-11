public interface PaymentStrategy {
    void pay(Transaction transaction, PaymentRequest request);
}
