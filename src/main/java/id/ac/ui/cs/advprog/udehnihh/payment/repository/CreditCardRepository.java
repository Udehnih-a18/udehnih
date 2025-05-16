package id.ac.ui.cs.advprog.udehnihh.payment.repository;

import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCardTransaction;

public class CreditCardRepository extends TransactionRepository<CreditCardTransaction> {
    @Override
    public CreditCardTransaction create(CreditCardTransaction transaction) {
        super.transactions.add(transaction);
        return transaction;
    }
}
