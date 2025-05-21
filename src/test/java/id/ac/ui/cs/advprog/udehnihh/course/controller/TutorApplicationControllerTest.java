package id.ac.ui.cs.advprog.udehnihh.course.controller;

import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.service.TutorApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication.ApplicationStatus;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TutorApplicationControllerTest {

    @Mock
    private TutorApplicationServiceImpl tutorApplicationService;

    @InjectMocks
    private TutorApplicationController tutorApplicationController;

    private final String testToken = "testToken123";
    private final String authHeader = "Bearer " + testToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApply_Success() {
        // Act
        ResponseEntity<String> response = tutorApplicationController.apply(authHeader);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Application submitted successfully", response.getBody());
        verify(tutorApplicationService, times(1)).createApplication(testToken);
    }

    @Test
    void testApply_WithoutBearerPrefix() {
        // Arrange
        String invalidAuthHeader = testToken;

        // Act
        ResponseEntity<String> response = tutorApplicationController.apply(invalidAuthHeader);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Application submitted successfully", response.getBody());
        verify(tutorApplicationService, times(1)).createApplication(testToken);
    }

    @Test
    void testGetApplicationStatus_Success() {
        // Arrange
        TutorApplication mockApplication = new TutorApplication();
        mockApplication.setStatus(ApplicationStatus.PENDING);
        when(tutorApplicationService.getApplication(testToken)).thenReturn(mockApplication);

        // Act
        ResponseEntity<TutorApplication> response = tutorApplicationController.getApplicationStatus(authHeader);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(ApplicationStatus.PENDING, response.getBody().getStatus());
        verify(tutorApplicationService, times(1)).getApplication(testToken);
    }

    @Test
    void testGetApplicationStatus_NotFound() {
        // Arrange
        when(tutorApplicationService.getApplication(testToken)).thenReturn(null);

        // Act
        ResponseEntity<TutorApplication> response = tutorApplicationController.getApplicationStatus(authHeader);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(tutorApplicationService, times(1)).getApplication(testToken);
    }

    @Test
    void testDeleteApplication_Success() {
        // Act
        ResponseEntity<String> response = tutorApplicationController.deleteApplication(authHeader);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Application deleted successfully", response.getBody());
        verify(tutorApplicationService, times(1)).deleteApplication(testToken);
    }

    @Test
    void testDeleteApplication_WithoutBearerPrefix() {
        // Arrange
        String invalidAuthHeader = testToken;

        // Act
        ResponseEntity<String> response = tutorApplicationController.deleteApplication(invalidAuthHeader);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Application deleted successfully", response.getBody());
        verify(tutorApplicationService, times(1)).deleteApplication(testToken);
    }
}