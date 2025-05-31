//package id.ac.ui.cs.advprog.udehnihh.payment.service;
//
//import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
//import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
//import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
//import id.ac.ui.cs.advprog.udehnihh.course.repository.CbCourseRepository;
//import id.ac.ui.cs.advprog.udehnihh.course.repository.CourseCreationRepository;
//import id.ac.ui.cs.advprog.udehnihh.payment.enums.AvailableBanks;
//import id.ac.ui.cs.advprog.udehnihh.payment.enums.TransactionStatus;
//import id.ac.ui.cs.advprog.udehnihh.payment.model.BankTransfer;
//import id.ac.ui.cs.advprog.udehnihh.payment.model.CreditCard;
//import id.ac.ui.cs.advprog.udehnihh.payment.model.Transaction;
//import id.ac.ui.cs.advprog.udehnihh.payment.repository.TransactionRepository;
//import id.ac.ui.cs.advprog.udehnihh.payment.factory.PaymentStrategyFactory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class PaymentServiceTest {
//
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @Mock
//    private PaymentStrategyFactory strategyFactory;
//
//    @Mock
//    private CourseCreationRepository courseCreationRepository;
//
//    @Mock
//    private CbCourseRepository cbCourseRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private PaymentServiceImpl paymentService;
//
//    private User mockUser;
//    private Course mockCourse;
//    private UUID courseId;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        courseId = UUID.randomUUID();
//        mockUser = new User();
//        mockUser.setId(UUID.randomUUID());
//
//        mockCourse = new Course();
//    }
//
//    @Test
//    void testCreateBankTransfer_Success() {
//        when(cbCourseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
//        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
//        when(cbCourseRepository.getById(courseId)).thenReturn(mockCourse);
//
//        BankTransfer expectedTransfer = new BankTransfer(mockCourse, mockUser, AvailableBanks.BANK_SENDIRI);
//        when(transactionRepository.save(any(BankTransfer.class))).thenReturn(expectedTransfer);
//
//        BankTransfer result = paymentService.createBankTransfer(courseId, mockUser, AvailableBanks.BANK_SENDIRI);
//        assertEquals(expectedTransfer, result);
//    }
//
//    @Test
//    void testCreateCreditCard_Success() {
//        when(cbCourseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
//        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
//        when(cbCourseRepository.getById(courseId)).thenReturn(mockCourse);
//
//        String accountNumber = "1234567812345678";
//        String cvc = "123";
//
//        CreditCard card = new CreditCard(mockCourse, mockUser, accountNumber, cvc);
//        card.setStatus(TransactionStatus.PAID);
//        when(transactionRepository.save(any(CreditCard.class))).thenReturn(card);
//
//        CreditCard result = paymentService.createCreditCard(courseId, mockUser, accountNumber, cvc);
//        assertEquals(card, result);
//        assertEquals(TransactionStatus.PAID, result.getStatus());
//    }
//
//    @Test
//    void testValidateData_ThrowsWhenCourseMissing() {
//        when(cbCourseRepository.findById(courseId)).thenReturn(Optional.empty());
//        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
//
//        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
//                paymentService.validateData(courseId, mockUser));
//
//        assertEquals("Course not found", ex.getMessage());
//    }
//
//    @Test
//    void testValidateData_ThrowsWhenStudentMissing() {
//        when(cbCourseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
//        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.empty());
//
//        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
//                paymentService.validateData(courseId, mockUser));
//
//        assertEquals("Student not found", ex.getMessage());
//    }
//
//    @Test
//    void testCreateCreditCard_ThrowsIfInvalidCard() {
//        when(cbCourseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
//        when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));
//
//        // Invalid account number
//        Exception ex1 = assertThrows(IllegalArgumentException.class, () ->
//                paymentService.createCreditCard(courseId, mockUser, "abc", "123"));
//        assertEquals("Account number must be 16 digits", ex1.getMessage());
//
//        // Invalid cvc
//        Exception ex2 = assertThrows(IllegalArgumentException.class, () ->
//                paymentService.createCreditCard(courseId, mockUser, "1234567812345678", "12"));
//        assertEquals("CVC must be 3 digits", ex2.getMessage());
//    }
//}
