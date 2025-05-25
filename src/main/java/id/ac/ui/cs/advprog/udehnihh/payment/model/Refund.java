package id.ac.ui.cs.advprog.udehnihh.payment.model;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "refund")
public class Refund {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.EAGER)
    private Transaction transaction;

    @Column(name = "reason")
    private String reasonForRefund;

    @Column(name = "status", nullable = false)
    private RefundStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Refund(Transaction transaction, String reasonForRefund) {
        this.transaction = transaction;
        this.reasonForRefund = reasonForRefund;
        this.status = RefundStatus.ON_HOLD;
    }

    public Refund() {
        this.status = RefundStatus.ON_HOLD;
    }
}
