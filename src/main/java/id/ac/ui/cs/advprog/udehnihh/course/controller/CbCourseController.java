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

    // daftar & search - bisa diakses tanpa login
    @GetMapping("/courses")
    public List<CourseSummaryDto> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return courseSvc.search(q, minPrice, maxPrice);
    }

    // detail - bisa diakses tanpa login
    @GetMapping("/courses/{id}")
    public CourseDetailDto detail(@PathVariable UUID id) {
        return courseSvc.getDetail(id);
    }

    // enroll - perlu login
    @PostMapping("/courses/{id}/enroll")
    public ResponseEntity<?> enroll(@PathVariable UUID id,
                                   @AuthenticationPrincipal User student) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Anda perlu login terlebih dahulu");
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

    // daftar kursus yg sudah di‑enrol - perlu login
    @GetMapping("/enrollments/me")
    public ResponseEntity<?> myEnrollments(@AuthenticationPrincipal User student) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Anda perlu login terlebih dahulu");
        }
        
        List<EnrollmentDto> enrollments = enrollSvc.myCourses(student);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/my-courses")
    public ResponseEntity<?> myCourses(@AuthenticationPrincipal User student) {
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Anda perlu login terlebih dahulu");
        }
        
        List<EnrollmentDto> courses = enrollSvc.myCourses(student);
        return ResponseEntity.ok(courses);
    }
}