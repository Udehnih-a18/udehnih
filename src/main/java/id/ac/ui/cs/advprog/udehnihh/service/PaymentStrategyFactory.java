@Component
public class PaymentStrategyFactory {
    @Autowired
    private BankTransferPaymentStrategy bankTransfer;

    @Autowired
    private CreditCardPaymentStrategy creditCard;

    public PaymentStrategy getStrategy(PaymentMethod method) {
        return switch (method) {
            case BANK_TRANSFER -> bankTransfer;
            case CREDIT_CARD -> creditCard;
        };
    }
}
