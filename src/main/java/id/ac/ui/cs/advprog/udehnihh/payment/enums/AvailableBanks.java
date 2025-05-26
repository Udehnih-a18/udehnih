package id.ac.ui.cs.advprog.udehnihh.payment.enums;

import java.util.Optional;

public enum AvailableBanks {
    BANK_SENDIRI("1234567890"),
    BANK_NASIONAL_WAKANDA("9876543210"),
    BANK_CAHAYA_ANTARTIKA("5555666677"),
    LAUTBANK("1111222233"),
    AMPUN_BANK_JAGO("9999888877");

    private final String accountNumber;

    AvailableBanks(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public static Optional<AvailableBanks> getAvailableBankByName(String name) {
        try {
            return Optional.of(AvailableBanks.valueOf(name));
        } catch (IllegalArgumentException | NullPointerException e) {
            return Optional.empty();
        }
    }

}
