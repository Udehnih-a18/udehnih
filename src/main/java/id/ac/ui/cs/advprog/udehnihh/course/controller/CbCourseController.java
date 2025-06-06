package id.ac.ui.cs.advprog.udehnihh.course.controller;

import id.ac.ui.cs.advprog.udehnihh.course.dto.*;
import id.ac.ui.cs.advprog.udehnihh.course.service.*;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cb")
@RequiredArgsConstructor
public class CbCourseController {

    private final CbCourseService courseSvc;
    private final CbEnrollmentService enrollSvc;
    private static final String NEED_LOGIN = "Anda perlu login terlebih dahulu";

    @GetMapping("/courses")
    public List<CourseSummaryDto> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return courseSvc.search(q, minPrice, maxPrice);
    }

    @GetMapping("/courses/{id}")
    public CourseDetailDto detail(@PathVariable UUID id) {
        return courseSvc.getDetail(id);
    }

    @PostMapping("/courses/{id}/enroll")
    public ResponseEntity<?> enroll(@PathVariable UUID id,
                                   @AuthenticationPrincipal User student) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(NEED_LOGIN);
        }
        
        try {
            UUID enrollmentId = enrollSvc.enroll(student, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentId);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/enrollments/me")
    public ResponseEntity<?> myEnrollments(@AuthenticationPrincipal User student) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(NEED_LOGIN);
        }
        
        List<EnrollmentDto> enrollments = enrollSvc.myCourses(student);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/my-courses")
    public ResponseEntity<?> myCourses(@AuthenticationPrincipal User student) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(NEED_LOGIN);
        }
        
        List<EnrollmentDto> courses = enrollSvc.myCourses(student);
        return ResponseEntity.ok(courses);
    }
}