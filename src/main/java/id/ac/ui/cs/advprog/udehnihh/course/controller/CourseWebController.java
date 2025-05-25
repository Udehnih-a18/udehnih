package id.ac.ui.cs.advprog.udehnihh.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/courses")
public class CourseWebController {

    @GetMapping("/tutor/apply")
    public String showTutorRegisterPage() {
        return "course/tutor_register";
    }

    @GetMapping("/tutor/applicant")
    public String showTutorApplicantPage() {
        return "course/tutor_applicant";
    }

    @GetMapping("/tutor/courseslist")
    public String showTutorCourseListPage() {
        return "course/tutor_courses_list";
    }

    @GetMapping("/{courseId}/detail")
    public String showTutorCourseDetailPage(@PathVariable("courseId") String courseId) {
        return "course/tutor_course_detail";
    }

    @GetMapping("/tutor/create")
    public String showTutorCreateCoursePage() {
        return "course/tutor_add_course";
    }
}
