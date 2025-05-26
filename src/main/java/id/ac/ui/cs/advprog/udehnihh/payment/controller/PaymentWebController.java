package id.ac.ui.cs.advprog.udehnihh.payment.controller;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CbCourseRepository;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.repository.TransactionRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PaymentWebController {

    private final CbCourseRepository cbCourseRepository;
    private final TransactionRepository transactionRepository;

    @GetMapping("/courses/{courseId}/payment")
    public String showCheckoutForm(@PathVariable UUID courseId) {
        Course course = cbCourseRepository.getReferenceById(courseId);
        return "payment/checkout";
    }

    @GetMapping("/transaction-history")
    public String showTransactionHistory() {
        return "payment/transaction-history";
    }

    @GetMapping("/transaction/{transactionId}/refund")
    public String showRefundForm(UUID transactionId) {
        Transaction transaction = transactionRepository.getReferenceById(transactionId);
        return "payment/refund";
    }
}
