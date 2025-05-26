package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.JwtService;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.TutorApplicationRequest;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.repository.TutorApplicationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TutorApplicationServiceImplTest {

    @Mock
    private TutorApplicationRepository tutorApplicationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TutorApplicationServiceImpl tutorApplicationService;

    private String token;
    private User user;
    private TutorApplicationRequest request;

    @BeforeEach
    void setUp() {
        token = "valid.jwt.token";
        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("user@example.com");
        user.setRole(Role.STUDENT);
        user.setFullName("Test User");

        request = new TutorApplicationRequest();
        request.setExperience("3 years experience");
        request.setMotivation("I love teaching");
    }

    @Test
    void testCreateApplicationSuccess() {
        when(jwtService.getEmailFromToken(token)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(tutorApplicationRepository.existsByApplicant(user)).thenReturn(false);
        when(tutorApplicationRepository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        TutorApplication application = tutorApplicationService.createApplication(token, request);

        assertNotNull(application);
        assertEquals(user, application.getApplicant());
        assertEquals("3 years experience", application.getExperience());
        assertEquals("I love teaching", application.getMotivation());
        assertEquals(TutorApplication.ApplicationStatus.PENDING, application.getStatus());
    }

    @Test
    void testCreateApplicationFailsIfUserNotStudent() {
        user.setRole(Role.TUTOR);
        when(jwtService.getEmailFromToken(token)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(IllegalStateException.class, () ->
                tutorApplicationService.createApplication(token, request));

        assertEquals("Only students can apply as tutor", exception.getMessage());
    }

    @Test
    void testCreateApplicationFailsIfUserNotFound() {
        when(jwtService.getEmailFromToken(token)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () ->
                tutorApplicationService.createApplication(token, request));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testCreateApplicationFailsIfAlreadyApplied() {
        when(jwtService.getEmailFromToken(token)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(tutorApplicationRepository.existsByApplicant(user)).thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                tutorApplicationService.createApplication(token, request));

        assertEquals("User has already applied", exception.getMessage());
    }

    @Test
    void testGetApplicationSuccess() {
        TutorApplication app = new TutorApplication();
        app.setApplicant(user);
        when(jwtService.getEmailFromToken(token)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(tutorApplicationRepository.findByApplicant(user)).thenReturn(Optional.of(app));

        TutorApplication result = tutorApplicationService.getApplication(token);
        assertEquals(app, result);
    }

    @Test
    void testGetApplicationFailsIfUserNotFound() {
        when(jwtService.getEmailFromToken(token)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () ->
                tutorApplicationService.getApplication(token));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetApplicationFailsIfApplicationNotFound() {
        when(jwtService.getEmailFromToken(token)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(tutorApplicationRepository.findByApplicant(user)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () ->
                tutorApplicationService.getApplication(token));

        assertEquals("No application found", exception.getMessage());
    }

    @Test
    void testDeleteApplicationSuccess() {
        TutorApplication app = new TutorApplication();
        app.setApplicant(user);
        when(jwtService.getEmailFromToken(token)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(tutorApplicationRepository.findByApplicant(user)).thenReturn(Optional.of(app));

        tutorApplicationService.deleteApplication(token);
        verify(tutorApplicationRepository).delete(app);
    }

    @Test
    void testDeleteApplicationFailsIfUserNotFound() {
        when(jwtService.getEmailFromToken(token)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () ->
                tutorApplicationService.deleteApplication(token));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testDeleteApplicationFailsIfApplicationNotFound() {
        when(jwtService.getEmailFromToken(token)).thenReturn(user.getEmail());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(tutorApplicationRepository.findByApplicant(user)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () ->
                tutorApplicationService.deleteApplication(token));

        assertEquals("No application found", exception.getMessage());
    }
}
