package id.ac.ui.cs.advprog.udehnihh.course.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {

    @Test
    void testAlreadyEnrolledExceptionDefaultConstructor() {
        AlreadyEnrolledException ex = new AlreadyEnrolledException();
        assertNotNull(ex);
    }

    @Test
    void testAlreadyEnrolledExceptionWithMessage() {
        String message = "You are already enrolled.";
        AlreadyEnrolledException ex = new AlreadyEnrolledException(message);
        assertEquals(message, ex.getMessage());
    }

    @Test
    void testResourceNotFoundException() {
        String message = "Resource not found.";
        ResourceNotFoundException ex = new ResourceNotFoundException(message);
        assertEquals(message, ex.getMessage());
    }
}