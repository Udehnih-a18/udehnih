package id.ac.ui.cs.advprog.udehnihh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.ac.ui.cs.advprog.udehnihh.service.EnrollmentService;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/{courseId}")
    public String enrollCourse(@PathVariable Long courseId, Authentication authentication) {
        User student = (User) authentication.getPrincipal();
        enrollmentService.enrollStudentToCourse(student, courseId);
        return "redirect:/courses/" + courseId;
    }

    @GetMapping("/my-courses")
    public String myCourses(Authentication authentication, Model model) {
        User student = (User) authentication.getPrincipal();
        model.addAttribute("enrollments", enrollmentService.getEnrollmentsByStudent(student));
        return "my-courses";
    }
}