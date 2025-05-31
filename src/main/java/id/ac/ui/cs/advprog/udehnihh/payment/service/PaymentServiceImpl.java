package id.ac.ui.cs.advprog.udehnihh.payment.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CbCourseRepository;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CourseCreationRepository;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
import id.ac.ui.cs.advprog.udehnihh.payment.model.BankTransfer;
import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCard;
import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
import id.ac.ui.cs.advprog.udehnihh.payment.repository.TransactionRepository;
import id.ac.ui.cs.advprog.udehnihh.payment.strategy.PaymentStrategy;
import id.ac.ui.cs.advprog.udehnihh.payment.factory.PaymentStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PaymentStrategyFactory strategyFactory;

    @Autowired
    private CourseCreationRepository courseCreationRepository;

    @Autowired
    private CbCourseRepository cbCourseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public PaymentServiceImpl(PaymentStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public void validateData(UUID courseId, User student) {
        if (courseId == null || student == null) {
            throw new IllegalArgumentException("Course or Student cannot be null");
        }

        Optional<Course> courseOpt = cbCourseRepository.findById(courseId);
        if (courseOpt.isEmpty()) {
            throw new IllegalArgumentException("Course not found");
        }

        Optional<User> studentOpt = userRepository.findById(student.getId());
        if (studentOpt.isEmpty()) {
            throw new IllegalArgumentException("Student not found");
        }

    }

    public BankTransfer createBankTransfer(UUID transactionId, UUID courseId, User student, AvailableBanks bank) {
        validateData(courseId, student);

        Optional<AvailableBanks> bankOpt = AvailableBanks.getAvailableBankByName(bank.name());
        if (bankOpt.isEmpty()) {
            throw new IllegalArgumentException("Bank not found");
        }

        Course course = cbCourseRepository.getById(courseId);
        BankTransfer transfer = new BankTransfer(transactionId, course, student, bank);
        return (BankTransfer) transactionRepository.save(transfer);
    }

    public CreditCard createCreditCard(UUID transactionId, UUID courseId, User student, String accountNumber, String cvc) {
        validateData(courseId, student);

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be blank");
        }

        if (!accountNumber.matches("\\d{16}")) {
            throw new IllegalArgumentException("Account number must be 16 digits");
        }

        if (cvc == null || cvc.isBlank()) {
            throw new IllegalArgumentException("CVC cannot be blank");
        }

        if (!cvc.matches("\\d{3}")) {
            throw new IllegalArgumentException("CVC must be 3 digits");
        }

        Course course = cbCourseRepository.getById(courseId);
        CreditCard card = new CreditCard(transactionId, course, student, accountNumber, cvc);

        return (CreditCard) transactionRepository.save(card);
    }

    @Override
    public List<Transaction> getTransactionByStudent(User student) {
        return transactionRepository.findAllByStudent_Id(student.getId());
    }

    @Override
    public Transaction getTransactionById(UUID transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }

    @Override
    public void updateTransactionStatus(UUID transactionId, TransactionStatus status) {
        transactionRepository.findByTransactionId(transactionId).setStatus(status);
    }
}