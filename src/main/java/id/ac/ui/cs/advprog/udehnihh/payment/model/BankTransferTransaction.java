package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;

public class BankTransferTransaction extends Transaction {
    private AvailableBanks bank;
    private boolean alreadyTransferred;
}
