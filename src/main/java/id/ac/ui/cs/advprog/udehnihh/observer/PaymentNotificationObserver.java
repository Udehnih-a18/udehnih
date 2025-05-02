package id.ac.ui.cs.advprog.udehnihh.observer;
import id.ac.ui.cs.advprog.udehnihh.model.Enrollment;


public class PaymentNotificationObserver implements EnrollmentObserver {
    @Override
    public void onEnrollment(Enrollment enrollment) {
        if ("PENDING".equals(enrollment.getPaymentStatus())) {
            // Logic to send payment notification
            System.out.println("Payment needed for course: " + enrollment.getCourse().getName());
        }
    }
}