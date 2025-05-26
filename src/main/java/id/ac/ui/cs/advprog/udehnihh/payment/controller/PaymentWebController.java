//package id.ac.ui.cs.advprog.udehnihh.payment.controller;
//
//import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
//import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
//import id.ac.ui.cs.advprog.udehnihh.course.repository.CbCourseRepository;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@Controller
//@RequiredArgsConstructor
//public class PaymentWebController {
//
//    private final CbCourseRepository cbCourseRepository;
//
//    @GetMapping("/courses/{courseId}/payment")
//    public String showCheckoutForm(@PathVariable UUID courseId) {
//        Course course = cbCourseRepository.findById(courseId);
//        return "payment/checkout";
//    }
//
//    @GetMapping("/transaction-history")
//    public String showTransactionHistory() {
//        return "payment/transaction-history";
//    }
//
//    @GetMapping("/transaction/{transactionId}/refund")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/auth/login";
//    }
//}
