package id.ac.ui.cs.advprog.udehnihh.factory;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import java.util.ArrayList;

public class CourseFactory {
    public Course createFreeCourse(String name, String description, User tutor) {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setTutor(tutor);
        course.setPrice(0.0);
        course.setSections(new ArrayList<>());
        return course;
    }

    public Course createPaidCourse(String name, String description, User tutor, Double price) {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setTutor(tutor);
        course.setPrice(price);
        course.setSections(new ArrayList<>());
        return course;
    }
}