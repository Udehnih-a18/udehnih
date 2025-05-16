package id.ac.ui.cs.advprog.udehnihh.payment.factory;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
import id.ac.ui.cs.advprog.udehnihh.payment.strategy.BankTransferPaymentStrategy;
import id.ac.ui.cs.advprog.udehnihh.payment.strategy.CreditCardPaymentStrategy;
import id.ac.ui.cs.advprog.udehnihh.payment.strategy.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
