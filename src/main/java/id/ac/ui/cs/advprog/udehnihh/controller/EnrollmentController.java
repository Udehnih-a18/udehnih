package id.ac.ui.cs.advprog.udehnihh.controller;

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