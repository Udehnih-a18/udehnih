package id.ac.ui.cs.advprog.udehnihh.repository;
import java.util.List;
import java.util.Optional;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import id.ac.ui.cs.advprog.udehnihh.model.Enrollment;

public interface EnrollmentRepository {
    List<Enrollment> findByStudent(User student);
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
    Enrollment save(Enrollment enrollment);
    void delete(Enrollment enrollment);
}
