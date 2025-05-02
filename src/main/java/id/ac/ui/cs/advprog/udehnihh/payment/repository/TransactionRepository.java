package id.ac.ui.cs.advprog.udehnihh.payment.repository;

import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public abstract class TransactionRepository<T extends Transaction> {
    protected List<T> transactions = new ArrayList<>();

    public T create(T transaction) {
        transactions.add(transaction);
        return transaction;
    }

    public Iterator<T> findAllTransactions() {
        return transactions.iterator();
    }

    public T findTransactionById(UUID id) {
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

    public List<Transaction> findByStudentId(UUID studentId) {
        return null;
    }
}