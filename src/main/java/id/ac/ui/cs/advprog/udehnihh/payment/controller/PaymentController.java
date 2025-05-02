package id.ac.ui.cs.advprog.udehnihh.payment.controller;

import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/create")
    public ResponseEntity<Transaction> pay(@RequestBody Transaction transaction) {
        Transaction tx = new Transaction();
        tx.setCourseName(transaction.getCourseName());
        tx.setTutorName(transaction.getTutorName());
        tx.setPrice(transaction.getPrice());
        tx.setMethod(transaction.getMethod());
        tx.setStatus(TransactionStatus.PENDING); // default
        tx.setStudent(transaction.getStudent()); // set student

        Transaction saved = service.createTransaction(tx, request);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/student/{id}")
    public List<Transaction> history(@PathVariable UUID id) {
        return service.getTransactionsForStudent(id);
    }
}
