package id.ac.ui.cs.advprog.udehnihh.payment.service;

public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private TransactionRepository repo;

    @Autowired
    private PaymentStrategyFactory strategyFactory;

    @Override
    public Transaction createTransaction(Transaction tx, PaymentRequest request) {
        PaymentStrategy strategy = strategyFactory.getStrategy(tx.getMethod());
        strategy.pay(tx, request);
        return repo.save(tx);
    }

    @Override
    public List<Transaction> getTransactionsHistory(UUID studentId) {
        return repo.findByStudentId(studentId);
    }
}