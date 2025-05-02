package id.ac.ui.cs.advprog.udehnihh.controller;

import id.ac.ui.cs.advprog.udehnihh.dto.*;
import id.ac.ui.cs.advprog.udehnihh.service.*;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    // 🔍  daftar & search
    @GetMapping("/courses")
    public List<CourseSummaryDto> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return courseSvc.search(q, minPrice, maxPrice);
    }

    // 👀  detail
    @GetMapping("/courses/{id}")
    public CourseDetailDto detail(@PathVariable UUID id) {
        return courseSvc.getDetail(id);
    }

    // 💸  enrol
    @PostMapping("/courses/{id}/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID enroll(@PathVariable UUID id,
                       @AuthenticationPrincipal User student) {
        return enrollSvc.enroll(student, id);
    }

    // 📋  daftar kursus yg sudah di‑enrol
    @GetMapping("/my-courses")
    public List<EnrollmentDto> myCourses(@AuthenticationPrincipal User student) {
        return enrollSvc.myCourses(student);
    }
}