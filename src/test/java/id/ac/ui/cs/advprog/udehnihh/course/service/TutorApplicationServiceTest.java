package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.JwtService;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.repository.TutorApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TutorApplicationServiceTest {

    @InjectMocks
    private TutorApplicationServiceImpl tutorApplicationService;

    @Mock
    private TutorApplicationRepository tutorApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    private User studentUser;
    private final String token = "test.token";
    private final String email = "student@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        studentUser = new User();
        studentUser.setId(UUID.randomUUID());
        studentUser.setEmail(email);
        studentUser.setRole(Role.STUDENT);
    }

    @Test
    void testCreateApplicationSuccess() {
        when(jwtService.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(studentUser));
        when(tutorApplicationRepository.existsByApplicant(studentUser)).thenReturn(false);
        when(tutorApplicationRepository.save(any(TutorApplication.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TutorApplication app = tutorApplicationService.createApplication(token);

        assertNotNull(app);
        assertEquals(studentUser, app.getApplicant());
        assertEquals(TutorApplication.ApplicationStatus.PENDING, app.getStatus());
    }

    @Test
    void testCreateApplicationUserNotFound() {
        when(jwtService.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> tutorApplicationService.createApplication(token));
    }

    @Test
    void testCreateApplicationAlreadyApplied() {
        when(jwtService.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(studentUser));
        when(tutorApplicationRepository.existsByApplicant(studentUser)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> tutorApplicationService.createApplication(token));
    }

    @Test
    void testCreateApplicationNotAStudent() {
        studentUser.setRole(Role.TUTOR);
        when(jwtService.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(studentUser));

        assertThrows(IllegalStateException.class, () -> tutorApplicationService.createApplication(token));
    }

    @Test
    void testGetApplicationSuccess() {
        TutorApplication app = new TutorApplication();
        app.setApplicant(studentUser);

        when(jwtService.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(studentUser));
        when(tutorApplicationRepository.findByApplicant(studentUser)).thenReturn(Optional.of(app));

        TutorApplication result = tutorApplicationService.getApplication(token);

        assertEquals(app, result);
    }

    @Test
    void testGetApplicationNotFound() {
        when(jwtService.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(studentUser));
        when(tutorApplicationRepository.findByApplicant(studentUser)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> tutorApplicationService.getApplication(token));
    }

    @Test
    void testDeleteApplicationSuccess() {
        TutorApplication app = new TutorApplication();
        app.setApplicant(studentUser);

        when(jwtService.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(studentUser));
        when(tutorApplicationRepository.findByApplicant(studentUser)).thenReturn(Optional.of(app));

        tutorApplicationService.deleteApplication(token);

        verify(tutorApplicationRepository, times(1)).delete(app);
    }

    @Test
    void testDeleteApplicationApplicationNotFound() {
        when(jwtService.getEmailFromToken(token)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(studentUser));
        when(tutorApplicationRepository.findByApplicant(studentUser)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> tutorApplicationService.deleteApplication(token));
    }
}
