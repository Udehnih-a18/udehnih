@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/create")
    public ResponseEntity<Transaction> pay(@RequestBody PaymentRequest request) {
        Transaction tx = new Transaction();
        tx.setCourseName(request.getCourseName());
        tx.setTutorName(request.getTutorName());
        tx.setPrice(request.getPrice());
        tx.setMethod(request.getMethod());
        tx.setStatus(TransactionStatus.PENDING); // default
        tx.setStudent(request.getStudent()); // set student

        Transaction saved = service.createTransaction(tx, request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/student/{id}")
    public List<Transaction> history(@PathVariable UUID id) {
        return service.getTransactionsForStudent(id);
    }
}
