package id.ac.ui.cs.advprog.udehnihh.dashboard.controller;

import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.dashboard.dto.TutorListRequest;
import id.ac.ui.cs.advprog.udehnihh.dashboard.dto.TutorDetailRequest;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.repository.TutorApplicationRepository;
import id.ac.ui.cs.advprog.udehnihh.course.service.CourseCreationService;
import id.ac.ui.cs.advprog.udehnihh.course.service.TutorApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff-dashboard")
@PreAuthorize("hasAuthority('STAFF')")
@RequiredArgsConstructor
public class StaffDashboardController {

    // Services & Repositories
    private final TutorApplicationService tutorApplicationService;
    private final CourseCreationService courseCreationService;
    private final TutorApplicationRepository tutorApplicationRepository;

    // ==================== Tutor Management ====================

    @GetMapping("/tutors")
    public ResponseEntity<TutorListRequest> getAllTutorApplications() {
        return ResponseEntity.ok(tutorApplicationService.convertToTutorListRequest());
    }

    @GetMapping("/tutors/{applicationId}")
    public ResponseEntity<TutorDetailRequest> getTutorApplicationDetails(
            @PathVariable UUID applicationId) {
        TutorApplication application = tutorApplicationRepository.findById(applicationId).orElseThrow();
        return ResponseEntity.ok(convertToDetailDTO(application));
    }

    @PatchMapping("/tutor-applications/{applicationId}/approve")
    public ResponseEntity<Void> approveTutorApplication(
            @PathVariable UUID applicationId) {
        tutorApplicationService.approveTutorApplication(applicationId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/tutor-applications/{applicationId}/reject")
    public ResponseEntity<Void> rejectTutorApplication(
            @PathVariable UUID applicationId,
            @RequestParam String reason) {
        tutorApplicationService.rejectTutorApplication(applicationId, reason);
        return ResponseEntity.ok().build();
    }

    // ==================== Course Management ====================
    @GetMapping("/courses")
    public ResponseEntity<List<CourseRequest>> getAllCourses() {
        return ResponseEntity.ok(courseCreationService.getAllCoursesDTO());
    }

    @PatchMapping("/courses/{courseId}/approve")
    public ResponseEntity<Course> approveCourse(
            @PathVariable UUID courseId) {
        return ResponseEntity.ok(courseCreationService.approveCourse(courseId));
    }

    @PatchMapping("/courses/{courseId}/reject")
    public ResponseEntity<Course> rejectCourse(@PathVariable UUID courseId) {
        return ResponseEntity.ok(courseCreationService.rejectCourse(courseId));
    }

    // ==================== Helper Methods ====================
    private TutorDetailRequest convertToDetailDTO(TutorApplication application) {
        return TutorDetailRequest.builder()
                .fullName(application.getApplicant().getFullName())
                .email(application.getApplicant().getEmail())
                .status(application.getStatus())
                .registrationDate(application.getApplicant().getRegistrationDate())
                .build();
    }
}