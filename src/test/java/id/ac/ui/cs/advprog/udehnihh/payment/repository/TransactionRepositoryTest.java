package id.ac.ui.cs.advprog.udehnihh.payment.repository;

import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionRepositoryTest {

    private MockTransactionRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new MockTransactionRepository();
    }

    // --- Inner Transaction subclass for testing ---
    public static class TestTransaction extends Transaction {
        private UUID studentId;

        public TestTransaction(UUID id, UUID studentId) {
            this.setId(id);
            this.studentId = studentId;
        }

        public UUID getStudentId() {
            return studentId;
        }

        public void setStudentId(UUID studentId) {
            this.studentId = studentId;
        }
    }

    // --- Inner mock repository for testing ---
    public static class MockTransactionRepository extends TransactionRepository<TestTransaction> {
        @Override
        public List<Transaction> findByStudentId(UUID studentId) {
            List<Transaction> result = new ArrayList<>();
            for (TestTransaction tx : transactions) {
                if (tx.getStudentId().equals(studentId)) {
                    result.add(tx);
                }
            }
            return result;
        }
    }

    @Test
    public void testCreateTransaction() {
        UUID id = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        TestTransaction tx = new TestTransaction(id, studentId);

        TestTransaction created = repository.create(tx);
        assertEquals(tx, created);
    }

    @Test
    public void testFindAllTransactions() {
        TestTransaction tx1 = new TestTransaction(UUID.randomUUID(), UUID.randomUUID());
        TestTransaction tx2 = new TestTransaction(UUID.randomUUID(), UUID.randomUUID());

        repository.create(tx1);
        repository.create(tx2);

        Iterator<TestTransaction> iterator = repository.findAllTransactions();
        List<TestTransaction> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);

        assertEquals(2, list.size());
        assertTrue(list.contains(tx1));
        assertTrue(list.contains(tx2));
    }

    @Test
    public void testFindTransactionById() {
        UUID id = UUID.randomUUID();
        TestTransaction tx = new TestTransaction(id, UUID.randomUUID());
        repository.create(tx);

        TestTransaction found = repository.findTransactionById(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testFindTransactionById_NotFound() {
        TestTransaction found = repository.findTransactionById(UUID.randomUUID());
        assertNull(found);
    }

    @Test
    public void testDeleteTransaction() {
        TestTransaction tx = new TestTransaction(UUID.randomUUID(), UUID.randomUUID());
        repository.create(tx);

        repository.delete(tx);
        assertNull(repository.findTransactionById(tx.getId()));
    }

    @Test
    public void testFindByStudentId() {
        UUID studentId = UUID.randomUUID();
        TestTransaction tx1 = new TestTransaction(UUID.randomUUID(), studentId);
        TestTransaction tx2 = new TestTransaction(UUID.randomUUID(), UUID.randomUUID());

        repository.create(tx1);
        repository.create(tx2);

        List<Transaction> result = repository.findByStudentId(studentId);
        assertEquals(1, result.size());
        assertEquals(studentId, ((TestTransaction) result.get(0)).getStudentId());
    }
}
