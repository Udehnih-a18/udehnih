package id.ac.ui.cs.advprog.udehnihh.observer;
import id.ac.ui.cs.advprog.udehnihh.model.Enrollment;

public class EnrollmentStatisticsObserver implements EnrollmentObserver {
    @Override
    public void onEnrollment(Enrollment enrollment) {
        // Logic to update enrollment statistics
        System.out.println("Updating statistics for course: " + enrollment.getCourse().getName());
    }
}