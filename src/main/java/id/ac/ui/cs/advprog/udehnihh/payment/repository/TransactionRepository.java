package id.ac.ui.cs.advprog.udehnihh.payment.repository;

import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public abstract class TransactionRepository<T extends Transaction> {
    private List<T> transactions = new ArrayList<>();

    public T create(T transaction) {
        transactions.add(transaction);
        return transaction;
    }

    public Iterator<T> findAllTransactions() {
        return transactions.iterator();
    }

    public T findTransactionById(int id) {
        for (T transaction : transactions) {
            if (transaction.getId().equals(id)) {
                return transaction;
            }
        }
        return null;
    }

    public void delete(T transaction) {
        transactions.remove(transaction);
    }
}