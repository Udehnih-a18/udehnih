package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.PaymentMethod;
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
