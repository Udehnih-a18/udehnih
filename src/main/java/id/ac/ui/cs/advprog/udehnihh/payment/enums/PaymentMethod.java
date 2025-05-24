package id.ac.ui.cs.advprog.udehnihh.payment.enums;

public enum PaymentMethod {
    BANK_TRANSFER("BANK_TRANSFER"),
    CREDIT_CARD("CREDIT_CARD");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }
}
