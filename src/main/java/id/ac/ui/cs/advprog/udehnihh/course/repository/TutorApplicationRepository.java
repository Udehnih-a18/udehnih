package id.ac.ui.cs.advprog.udehnihh.course.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import id.ac.ui.cs.advprog.udehnihh.dashboard.dto.TutorListRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication.ApplicationStatus;

public interface TutorApplicationRepository extends JpaRepository<TutorApplication, UUID> {
    Optional<TutorApplication> findByApplicant(User applicant);
    Optional<TutorApplication> findTutorApplicationsByStatus(ApplicationStatus status);
    boolean existsByApplicant(User user);
    void deleteByApplicant(User applicant);

    List<TutorApplication> findAll();
}
