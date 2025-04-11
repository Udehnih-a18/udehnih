
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String courseName;
    private String tutorName;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @ManyToOne
    private Student student;

    private LocalDateTime createdAt = LocalDateTime.now();
}
