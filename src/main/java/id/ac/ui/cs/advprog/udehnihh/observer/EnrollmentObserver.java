package id.ac.ui.cs.advprog.udehnihh.observer;
import id.ac.ui.cs.advprog.udehnihh.model.Enrollment;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import java.util.ArrayList;

public interface EnrollmentObserver {
    void onEnrollment(Enrollment enrollment);
}
