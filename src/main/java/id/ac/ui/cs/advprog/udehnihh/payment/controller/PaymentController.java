package id.ac.ui.cs.advprog.udehnihh.payment.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.AuthService;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CbCourseRepository;
import id.ac.ui.cs.advprog.udehnihh.payment.mapper.ObjectToMapMapper;
import id.ac.ui.cs.advprog.udehnihh.payment.model.BankTransfer;
import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCard;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Refund;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.service.PaymentServiceImpl;
import id.ac.ui.cs.advprog.udehnihh.payment.service.RefundServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final CbCourseRepository cbCourseRepository;

    @Autowired
    private final RefundServiceImpl refundService;

    @Autowired
    private final PaymentServiceImpl service;

    private final ObjectToMapMapper objectToMapMapper;
    @Autowired
    private AuthService authService;

    // bank transfer payment - perlu login
    @PostMapping("/courses/{courseId}/bank-transfer/{transactionId}")
    public ResponseEntity<?> createBankTransfer(@PathVariable UUID courseId,
                                    @PathVariable UUID transactionId,
                                    @RequestBody HashMap<String, String> newTransaction) {
        User student = authService.getCurrentUser();
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Anda perlu login terlebih dahulu");
        }

        try {
            AvailableBanks bank = AvailableBanks.valueOf(newTransaction.get("bank"));

            BankTransfer bankTransfer = service.createBankTransfer(transactionId, courseId, student, bank);

            HashMap<String, Object> responseBody = ObjectToMapMapper.mapToObject(bankTransfer);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // credit card payment - perlu login
    @PostMapping("/courses/{courseId}/credit-card/{transactionId}")
    public ResponseEntity<?> createCreditCard(@PathVariable UUID courseId,
                                    @PathVariable UUID transactionId,
                                    @RequestBody HashMap<String, String> newTransaction) {

        User student = authService.getCurrentUser();
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Anda perlu login terlebih dahulu");
        }

        try {
            String accountNumber = newTransaction.get("accountNumber");
            String cvc = newTransaction.get("cvc");

            CreditCard creditCard = service.createCreditCard(transactionId, courseId, student, accountNumber, cvc);

            HashMap<String, Object> responseBody = ObjectToMapMapper.mapToObject(creditCard);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // riwayat payment - perlu login
    @GetMapping("/transaction-history")
    public ResponseEntity<?> paymentHistory() {
        User student = authService.getCurrentUser();
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
                                           @RequestHeader("Authorization") String contentType,
                                           @RequestBody Map<String, String> refundRequest) {

        User student = authService.getCurrentUser();
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

            HashMap<String, Object> responseBody = objectToMapMapper.mapToObject(refund);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}