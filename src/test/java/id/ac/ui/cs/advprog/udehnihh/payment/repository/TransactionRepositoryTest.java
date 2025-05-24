package id.ac.ui.cs.advprog.udehnihh.payment.repository;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionRepositoryTest {

    private TransactionRepository<Transaction> repository;

    @BeforeEach
    public void setUp() {
        repository = new TransactionRepository<Transaction>();
    }

    @Test
    public void testCreateTransaction() {
        Transaction tx = new Transaction();

        Transaction created = repository.create(tx);
        assertEquals(tx, created);
    }

    @Test
    public void testFindAllTransactions() {
        Transaction tx1 = new Transaction();
        Transaction tx2 = new Transaction();

        repository.create(tx1);
        repository.create(tx2);

        Iterator<Transaction> iterator = repository.findAllTransactions();
        List<Transaction> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);

        assertEquals(2, list.size());
        assertTrue(list.contains(tx1));
        assertTrue(list.contains(tx2));
    }

    @Test
    public void testFindTransactionById() {
        UUID id = UUID.randomUUID();
        Transaction tx = new Transaction();
        tx.setId(id);
        repository.create(tx);

        Transaction found = repository.findTransactionById(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testFindTransactionByIdNotFound() {
        Transaction found = repository.findTransactionById(UUID.randomUUID());
        assertNull(found);
    }

    @Test
    public void testDeleteTransaction() {
        Transaction tx = new Transaction();
        repository.create(tx);

        repository.delete(tx);
        assertNull(repository.findTransactionById(tx.getId()));
    }

    @Test
    public void testFindByStudentId() {
        UUID studentId = UUID.randomUUID();
        User student = new User();
        Transaction tx1 = new Transaction();
        tx1.setStudent(student);
        Transaction tx2 = new Transaction();

        repository.create(tx1);
        repository.create(tx2);

        List<Transaction> result = repository.findByStudentId(student.getId());
        assertEquals(1, result.size());
        assertEquals(studentId, (result.get(0)).getStudent().getId());
    }
}
