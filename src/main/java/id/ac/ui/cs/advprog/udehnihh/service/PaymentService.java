@Service
public class PaymentService {
    @Autowired
    private TransactionRepository repo;

    @Autowired
    private PaymentStrategyFactory strategyFactory;

    public Transaction createTransaction(Transaction tx, PaymentRequest request) {
        PaymentStrategy strategy = strategyFactory.getStrategy(tx.getMethod());
        strategy.pay(tx, request);
        return repo.save(tx);
    }

    public List<Transaction> getTransactionsForStudent(UUID studentId) {
        return repo.findByStudentId(studentId);
    }
}
