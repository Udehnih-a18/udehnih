package id.ac.ui.cs.advprog.udehnihh.course.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.enums.CourseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Course course;
    private User tutor;

    @BeforeEach
    void setUp() {
        tutor = new User();
        tutor.setId(UUID.randomUUID());
        tutor.setFullName("John Doe");
        tutor.setEmail("john@example.com");

        course = new Course();
        course.setId(UUID.randomUUID());
        course.setName("Advanced Java");
        course.setDescription("Learn advanced Java concepts");
        course.setPrice(299000.0);
        course.setTutor(tutor);
        course.setStatus(CourseStatus.PENDING_APPROVAL);
    }

    @Test
    void testCourseCreation() {
        assertNotNull(course);
        assertNotNull(course.getId());
        assertEquals("Advanced Java", course.getName());
        assertEquals("Learn advanced Java concepts", course.getDescription());
        assertEquals(299000.0, course.getPrice());
        assertEquals(tutor, course.getTutor());
        assertEquals(CourseStatus.PENDING_APPROVAL, course.getStatus());
        assertTrue(course.getSections().isEmpty());
    }

    @Test
    void testStatusTransitions() {
        // Test valid status transitions
        course.setStatus(CourseStatus.PUBLISHED);
        assertEquals(CourseStatus.PUBLISHED, course.getStatus());

        course.setStatus(CourseStatus.REJECTED);
        assertEquals(CourseStatus.REJECTED, course.getStatus());

        course.setStatus(CourseStatus.PENDING_APPROVAL);
        assertEquals(CourseStatus.PENDING_APPROVAL, course.getStatus());
    }

    @Test
    void testAddSections() {
        Section section1 = new Section();
        section1.setName("Introduction");
        section1.setCourse(course);

        Section section2 = new Section();
        section2.setName("Advanced Topics");
        section2.setCourse(course);

        course.getSections().add(section1);
        course.getSections().add(section2);

        assertEquals(2, course.getSections().size());
        assertTrue(course.getSections().contains(section1));
        assertTrue(course.getSections().contains(section2));
        assertEquals(course, section1.getCourse());
        assertEquals(course, section2.getCourse());
    }

    @Test
    void testRemoveSection() {
        Section section = new Section();
        section.setName("Introduction");
        section.setCourse(course);
        course.getSections().add(section);

        course.getSections().remove(section);

        assertTrue(course.getSections().isEmpty());
        assertNull(section.getCourse()); // Due to orphanRemoval = true
    }

    @Test
    void testEqualsAndHashCode() {
        Course sameCourse = new Course();
        sameCourse.setId(course.getId());

        Course differentCourse = new Course();
        differentCourse.setId(UUID.randomUUID());

        assertEquals(course, sameCourse);
        assertNotEquals(course, differentCourse);
        assertEquals(course.hashCode(), sameCourse.hashCode());
    }

    @Test
    void testToString() {
        String courseString = course.toString();
        assertTrue(courseString.contains("Advanced Java"));
        assertTrue(courseString.contains(course.getId().toString()));
    }

    @Test
    void testNullChecks() {
        assertThrows(NullPointerException.class, () -> course.setName(null));
        assertThrows(NullPointerException.class, () -> course.setDescription(null));
        assertThrows(NullPointerException.class, () -> course.setTutor(null));
        assertThrows(NullPointerException.class, () -> course.setStatus(null));
    }

    @Test
    void testPriceValidation() {
        assertThrows(IllegalArgumentException.class, () -> course.setPrice(-100.0));
        assertThrows(IllegalArgumentException.class, () -> course.setPrice(null));
    }
}