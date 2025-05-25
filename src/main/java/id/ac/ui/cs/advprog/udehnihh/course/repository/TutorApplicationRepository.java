package id.ac.ui.cs.advprog.udehnihh.course.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

public interface TutorApplicationRepository extends JpaRepository<TutorApplication, UUID> {
    Optional<TutorApplication> findByApplicant(User applicant);
    boolean existsByApplicant(User user);
    void deleteByApplicant(User applicant);
}
