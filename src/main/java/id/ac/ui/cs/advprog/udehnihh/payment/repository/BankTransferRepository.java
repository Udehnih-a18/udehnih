package id.ac.ui.cs.advprog.udehnihh.payment.repository;

import id.ac.ui.cs.advprog.udehnihh.payment.model.BankTransferTransaction;

public class BankTransferRepository extends TransactionRepository<BankTransferTransaction> {
    @Override
    public BankTransferTransaction create(BankTransferTransaction transaction) {
        super.transactions.add(transaction);
        return transaction;
    }
}
