package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.RefundStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.BankTransfer;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Refund;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.repository.RefundRepository;
import id.ac.ui.cs.advprog.udehnihh.payment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefundServiceImpl implements RefundService {

    @Autowired
    private RefundRepository refundRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Refund createRefund(UUID transactionId, String reason) {
        if (transactionId == null) {
            throw new IllegalArgumentException("Transaction must not be null");
        }

        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new IllegalArgumentException("Transaction not found");
        }

        Transaction transaction = transactionRepository.getById(transactionId);
        Refund transfer = new Refund(transaction, reason);
        return refundRepository.save(transfer);
}

    @Override
    public Refund findByTransactionId(UUID transactionId) {
        return refundRepository.findRefundByTransaction_TransactionId((transactionId));
    }

    @Override
    public void updateRefundStatus(UUID refundId, RefundStatus status) {
        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        refund.setStatus(status);
        refund = refundRepository.save(refund);

    }
}
