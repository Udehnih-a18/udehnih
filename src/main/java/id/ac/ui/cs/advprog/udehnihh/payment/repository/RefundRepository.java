package id.ac.ui.cs.advprog.udehnihh.payment.repository;

import id.ac.ui.cs.advprog.udehnihh.payment.model.Refund;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class RefundRepository {
    List<Refund> refunds;

    public Refund createRefund(Refund rf) {
        refunds.add(rf);
        return rf;
    }

    public Iterator<Refund> findAllRefunds() {
        return refunds.iterator();
    }

    public Refund findRefundById(UUID id) {
        for (Refund refund : refunds) {
            if (refund.getTransaction().getId().equals(id)) {
                return refund;
            }
        }
        return null;
    }
}
