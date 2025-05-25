package id.ac.ui.cs.advprog.udehnihh.payment.controller;

import id.ac.ui.cs.advprog.udehnihh.payment.model.BankTransferTransaction;
import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCardTransaction;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentWebController {

    @Autowired
    private PaymentServiceImpl service;

    @GetMapping("/bankTransfer")
    public String bankTransferPage(Model model) {
        BankTransferTransaction bankTransferTransaction = new BankTransferTransaction();
        model.addAttribute("bankTransferTransaction", bankTransferTransaction);
        return "PayByBankTransfer";
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> payByBankTransfer(@ModelAttribute("bankTransferTransaction") BankTransferTransaction bankTransferTransaction) {
        Transaction saved = service.createCreditCardTransaction(bankTransferTransaction);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/creditCard")
    public String creditCardPage(Model model) {
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
        model.addAttribute("creditCardTransaction", creditCardTransaction);
        return "PayByCreditCard";
    }

    @PostMapping("/create")
    public ResponseEntity<Transaction> payByCreditCard(@ModelAttribute("bankTransferTransaction") CreditCardTransaction creditCardTransaction) {
        Transaction saved = service.createCreditCardTransaction(creditCardTransaction);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/transactionHistory")
    public List<Transaction> history(@PathVariable UUID id) {
        return service.getTransactionByStudent(id);
    }
}