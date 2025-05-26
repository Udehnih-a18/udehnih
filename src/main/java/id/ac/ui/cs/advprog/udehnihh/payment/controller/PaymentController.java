package id.ac.ui.cs.advprog.udehnihh.payment.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CbCourseRepository;
import id.ac.ui.cs.advprog.udehnihh.payment.model.BankTransfer;
import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCard;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Refund;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.service.PaymentServiceImpl;
import id.ac.ui.cs.advprog.udehnihh.payment.service.RefundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final CbCourseRepository cbCourseRepository;
    private final RefundService refundService;
    private PaymentServiceImpl service;

    public PaymentController(CbCourseRepository cbCourseRepository, RefundService refundService) {
        this.cbCourseRepository = cbCourseRepository;
        this.refundService = refundService;
    }

    // bank transfer payment - perlu login
    @PostMapping("/courses/{courseId}/payment/bank-transfer")
    public ResponseEntity<?> createBankTransfer(@PathVariable UUID courseId,
                                    @AuthenticationPrincipal User student,
                                    @RequestBody HashMap<String, String> newTransaction) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Anda perlu login terlebih dahulu");
        }

        try {
            AvailableBanks bank = AvailableBanks.valueOf(newTransaction.get("bank"));

            BankTransfer bankTransfer = service.createBankTransfer(courseId, student, bank);
            return ResponseEntity.status(HttpStatus.CREATED).body(bankTransfer.getTransactionId());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // credit card payment - perlu login
    @PostMapping("/courses/{courseId}/credit-card")
    public ResponseEntity<?> createCreditCard(@PathVariable UUID courseId,
                                    @AuthenticationPrincipal User student,
                                    @RequestBody HashMap<String, String> newTransaction) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Anda perlu login terlebih dahulu");
        }

        try {
            String accountNumber = newTransaction.get("accountNumber");
            String cvc = newTransaction.get("cvc");

            CreditCard creditCard = service.createCreditCard(courseId, student, accountNumber, cvc);
            return ResponseEntity.status(HttpStatus.CREATED).body(creditCard.getTransactionId());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // riwayat payment - perlu login
    @GetMapping("/transaction-history")
    public ResponseEntity<?> paymentHistory(@AuthenticationPrincipal User student) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Anda perlu login terlebih dahulu");
        }

        List<Transaction> courses = service.getTransactionByStudent(student);
        return ResponseEntity.ok(courses);
    }

    // minta refund - perlu login
    @PostMapping("/transaction-history/{transactionId}/refund")
    public ResponseEntity<?> requestRefund(@PathVariable UUID transactionId,
                                           @AuthenticationPrincipal User student,
                                           @RequestBody Map<String, String> refundRequest) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Anda perlu login terlebih dahulu");
        }

        try {
            String reasonForRefund;

            if (refundRequest.get("refundRequest") == null) {
                reasonForRefund = "";
            }
            else reasonForRefund = refundRequest.get("reasonForRefund");

            Refund refund = refundService.createRefund(transactionId, reasonForRefund);

            return ResponseEntity.status(HttpStatus.CREATED).body(refund.getId());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}