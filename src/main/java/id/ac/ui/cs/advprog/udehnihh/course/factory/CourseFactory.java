package id.ac.ui.cs.advprog.udehnihh.course.factory;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CourseFactory {
    public Course createFreeCourse(String name, String description, User tutor) {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setTutor(tutor);
        course.setPrice(new BigDecimal("0.0"));
        course.setSections(new ArrayList<>());
        return course;
    }

    public Course createPaidCourse(String name, String description, User tutor, BigDecimal price) {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setTutor(tutor);
        course.setPrice(price);
        course.setSections(new ArrayList<>());
        return course;
    }
}