package id.ac.ui.cs.advprog.udehnihh.course.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class TutorApplicationTest {

    private User testUser;
    private TutorApplication tutorApplication;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@student.com");
        testUser.setPassword("testpassword");

        tutorApplication = new TutorApplication();
    }

    @Test
    void testTutorApplicationCreation() {
        tutorApplication.setApplicant(testUser);
        tutorApplication.setStatus(TutorApplication.ApplicationStatus.PENDING);
        tutorApplication.setCreatedAt(LocalDateTime.now());

        assertNotNull(tutorApplication);
        assertEquals(testUser, tutorApplication.getApplicant());
        assertEquals(TutorApplication.ApplicationStatus.PENDING, tutorApplication.getStatus());
        assertNotNull(tutorApplication.getCreatedAt());
    }

    @Test
    void testTutorApplicationStatusEnum() {
        tutorApplication.setApplicant(testUser);
        tutorApplication.setStatus(TutorApplication.ApplicationStatus.PENDING);
        
        assertEquals(TutorApplication.ApplicationStatus.PENDING, tutorApplication.getStatus());

        tutorApplication.setStatus(TutorApplication.ApplicationStatus.ACCEPTED);
        assertEquals(TutorApplication.ApplicationStatus.ACCEPTED, tutorApplication.getStatus());

        tutorApplication.setStatus(TutorApplication.ApplicationStatus.DENIED);
        assertEquals(TutorApplication.ApplicationStatus.DENIED, tutorApplication.getStatus());
    }

    @Test
    void testApplicantSetting() {
        tutorApplication.setApplicant(testUser);
        assertEquals(testUser, tutorApplication.getApplicant());
    }

    @Test
    void testTutorApplicationStatusInitial() {
        tutorApplication.setApplicant(testUser);
        
        assertEquals(TutorApplication.ApplicationStatus.PENDING, tutorApplication.getStatus());
    }

    @Test
    void testApplicationStatusEnumValues() {
        TutorApplication.ApplicationStatus[] values = TutorApplication.ApplicationStatus.values();
        assertEquals(3, values.length);
        assertEquals(TutorApplication.ApplicationStatus.PENDING, TutorApplication.ApplicationStatus.valueOf("PENDING"));
        assertEquals(TutorApplication.ApplicationStatus.ACCEPTED, TutorApplication.ApplicationStatus.valueOf("ACCEPTED"));
        assertEquals(TutorApplication.ApplicationStatus.DENIED, TutorApplication.ApplicationStatus.valueOf("DENIED"));
    }

    @Test
    void testOnCreateMethodSetsCreatedAt() {
        TutorApplication newApp = new TutorApplication();
        newApp.onCreate();  // Simulasi pemanggilan PrePersist
        assertNotNull(newApp.getCreatedAt());
    }
}
