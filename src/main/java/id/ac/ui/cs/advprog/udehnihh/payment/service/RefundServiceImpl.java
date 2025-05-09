package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.RefundStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Refund;
import id.ac.ui.cs.advprog.udehnihh.payment.repository.RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RefundServiceImpl implements RefundService {
    @Autowired
    private RefundRepository refundRepository;

    @Override
    public Refund createRefund(Refund rf) {

        return null;
    }

    @Override
    public List<Refund> getRefundHistory(UUID studentId) {
        return List.of();
    }

    @Override
    public Refund findById(UUID transactionId) {
        return null;
    }

    @Override
    public void updateRefundStatus(UUID transactionId, RefundStatus status) {

    }

    @Override
    public void cancelRefund(UUID transactionId) {

    }
}
