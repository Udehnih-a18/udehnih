package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.RefundStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Refund;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface RefundService {
    public Refund createRefund(Refund rf);
    public List<Refund> getRefundHistory(UUID studentId);

    Refund findById(UUID transactionId);

    public void updateRefundStatus(UUID transactionId, RefundStatus status);
    public void cancelRefund(UUID transactionId);
}
