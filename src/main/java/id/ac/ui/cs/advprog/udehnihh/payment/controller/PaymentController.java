//package id.ac.ui.cs.advprog.udehnihh.payment.controller;
//
//import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
//import id.ac.ui.cs.advprog.udehnihh.course.dto.EnrollmentDto;
//import id.ac.ui.cs.advprog.udehnihh.course.repository.CbCourseRepository;
//import id.ac.ui.cs.advprog.udehnihh.payment.model.BankTransfer;
//import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCard;
//import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
//import id.ac.ui.cs.advprog.udehnihh.payment.service.PaymentServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/payments")
//public class PaymentController {
//
//    private final CbCourseRepository cbCourseRepository;
//    private PaymentServiceImpl service;
//
//    public PaymentController(CbCourseRepository cbCourseRepository) {
//        this.cbCourseRepository = cbCourseRepository;
//    }
//
//    // payment - perlu login
//    @PostMapping("/courses/{id}/payment")
//    public ResponseEntity<?> enroll(@PathVariable UUID courseId,
//                                    @AuthenticationPrincipal User student) {
//        if (student == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Anda perlu login terlebih dahulu");
//        }
//
//        try {
//            UUID enrollmentId = service.createBankTransfer(cbCourseRepository.getById(courseId), student, AvailableBanks.BCA);
//            return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentId);
//        } catch (IllegalStateException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // riwayat payment - perlu login
//    @GetMapping("/transactionHistory")
//    public ResponseEntity<?> paymentHistory(@AuthenticationPrincipal User student) {
//        if (student == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body("Anda perlu login terlebih dahulu");
//        }
//
//        List<Transaction> courses = service.getTransactionByStudent(student);
//        return ResponseEntity.ok(courses);
//    }
//
//    @GetMapping("/bankTransfer")
//    public ResponseEntity<Map<String, Object>> bankTransferPage() {
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Bank transfer payment form");
//        response.put("transaction", new BankTransfer());
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/bankTransfer/create")
//    public ResponseEntity<Transaction> payByBankTransfer(@RequestBody BankTransfer bankTransferTransaction) {
//        Transaction saved = service.createBankTransfer(bankTransferTransaction);
//        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//    }
//
//    @GetMapping("/creditCard")
//    public ResponseEntity<Map<String, Object>> creditCardPage() {
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Credit card payment form");
//        response.put("transaction", new CreditCard());
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/creditCard/create")
//    public ResponseEntity<Transaction> payByCreditCard(@RequestBody CreditCard creditCardTransaction) {
//        Transaction saved = service.createCreditCard(creditCardTransaction);
//        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//    }
//
//    @GetMapping("/transactionHistory/{id}")
//    public ResponseEntity<List<Transaction>> history(@PathVariable UUID id) {
//        List<Transaction> transactions = service.getTransactionByStudent(id);
//        return ResponseEntity.ok(transactions);
//    }
//}