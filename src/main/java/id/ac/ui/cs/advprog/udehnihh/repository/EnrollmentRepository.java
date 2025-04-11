package id.ac.ui.cs.advprog.udehnihh.repository;

public interface EnrollmentRepository {
    List<Enrollment> findByStudent(User student);
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);
    Enrollment save(Enrollment enrollment);
    void delete(Enrollment enrollment);
}
