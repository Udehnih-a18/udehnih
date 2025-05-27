package id.ac.ui.cs.advprog.udehnihh.course.factory;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

class CourseFactoryTest {

    private CourseFactory factory;
    private User tutor;

    @BeforeEach
    void setUp() {
        factory = new CourseFactory();
        tutor = new User();
        tutor.setFullName("Jane Doe");
        tutor.setEmail("jane@tutor.com");
    }

    @Test
    void testCreateFreeCourse() {
        String name = "Intro to Java";
        String description = "Learn Java basics";

        Course course = factory.createFreeCourse(name, description, tutor);

        assertEquals(name, course.getName());
        assertEquals(description, course.getDescription());
        assertEquals(tutor, course.getTutor());
        assertEquals(new BigDecimal("0.0"), course.getPrice());
        assertNotNull(course.getSections());
        assertTrue(course.getSections().isEmpty());
    }

    @Test
    void testCreatePaidCourse() {
        String name = "Advanced Spring";
        String description = "Master Spring Boot";
        BigDecimal price = new BigDecimal("350000.0");

        Course course = factory.createPaidCourse(name, description, tutor, price);

        assertEquals(name, course.getName());
        assertEquals(description, course.getDescription());
        assertEquals(tutor, course.getTutor());
        assertEquals(price, course.getPrice());
        assertNotNull(course.getSections());
        assertTrue(course.getSections().isEmpty());
    }
}
