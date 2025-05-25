package id.ac.ui.cs.advprog.udehnihh.course.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class CbCourseViewController {

    @GetMapping("/courses")
    public String courseList() {
        return "course/list";
    }

    @GetMapping("/courses/{id}")
    public String courseDetail(@PathVariable UUID id) {
        return "course/detail";
    }

    @GetMapping("/my-courses")
    public String myCourses() {
        return "course/my-courses";
    }
}
