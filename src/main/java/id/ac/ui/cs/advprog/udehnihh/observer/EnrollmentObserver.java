package id.ac.ui.cs.advprog.udehnihh.observer;

public interface EnrollmentObserver {
    void onEnrollment(Enrollment enrollment);
}

public class PaymentNotificationObserver implements EnrollmentObserver {
    @Override
    public void onEnrollment(Enrollment enrollment) {
        if ("PENDING".equals(enrollment.getPaymentStatus())) {
            // Logic to send payment notification
            System.out.println("Payment needed for course: " + enrollment.getCourse().getName());
        }
    }
}

public class EnrollmentStatisticsObserver implements EnrollmentObserver {
    @Override
    public void onEnrollment(Enrollment enrollment) {
        // Logic to update enrollment statistics
        System.out.println("Updating statistics for course: " + enrollment.getCourse().getName());
    }
}