package id.ac.ui.cs.advprog.udehnihh.course.exception;

public class AlreadyEnrolledException extends RuntimeException {
    public AlreadyEnrolledException() {
        super();
    }
    public AlreadyEnrolledException(String message) {
        super(message);
    }
}
