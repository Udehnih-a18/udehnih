package id.ac.ui.cs.advprog.udehnihh.payment.controller;

import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl service;

    @GetMapping("/bankTransfer")
    public ResponseEntity<Map<String, Object>> bankTransferPage() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Bank transfer payment form");
        response.put("transaction", new BankTransferTransaction());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bankTransfer/create")
    public ResponseEntity<Transaction> payByBankTransfer(@RequestBody BankTransferTransaction bankTransferTransaction) {
        Transaction saved = service.createCreditCardTransaction(bankTransferTransaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/creditCard")
    public ResponseEntity<Map<String, Object>> creditCardPage() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Credit card payment form");
        response.put("transaction", new CreditCardTransaction());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/creditCard/create")
    public ResponseEntity<Transaction> payByCreditCard(@RequestBody CreditCardTransaction creditCardTransaction) {
        Transaction saved = service.createCreditCardTransaction(creditCardTransaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/transactionHistory/{id}")
    public ResponseEntity<List<Transaction>> history(@PathVariable UUID id) {
        List<Transaction> transactions = service.getTransactionByStudent(id);
        return ResponseEntity.ok(transactions);
    }
}

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @GetMapping
    public ResponseEntity<List<TransactionSummaryDTO>> getAllTransactions() {
        // Clean list view without sensitive data
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransaction(@PathVariable UUID id) {
        Transaction transaction = transactionService.findById(id);

        if (transaction.getMethod() == PaymentMethod.BANK_TRANSFER) {
            return ResponseEntity.ok(convertToBankTransferDTO(transaction));
        } else {
            return ResponseEntity.ok(convertToCreditCardDTO(transaction));
        }
    }

    @PostMapping("/bank-transfer")
    public ResponseEntity<BankTransferResponseDTO> createBankTransfer(
            @Valid @RequestBody CreateBankTransferDTO dto) {
        // Safe, validated input
    }

    @PostMapping("/credit-card")
    public ResponseEntity<CreditCardResponseDTO> processCreditCard(
            @Valid @RequestBody CreateCreditCardDTO dto) {
        // CVC is used for processing but not stored/returned
    }
}