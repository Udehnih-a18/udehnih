package id.ac.ui.cs.advprog.udehnihh.payment.repository;

import id.ac.ui.cs.advprog.udehnihh.payment.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public interface RefundRepository extends JpaRepository<Refund, UUID> {
    public Refund findRefundById(UUID refundId);
    public Refund findRefundByTransaction_TransactionId(UUID transactionId);
}
