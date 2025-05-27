package id.ac.ui.cs.advprog.udehnihh.course.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CbCourseViewControllerTest {

    @InjectMocks
    private CbCourseViewController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void courseList_shouldReturnCorrectView() {
        String viewName = controller.courseList();
        assertEquals("course/list", viewName);
    }

    @Test
    void courseDetail_shouldReturnCorrectView() {
        UUID courseId = UUID.randomUUID();

        String viewName = controller.courseDetail(courseId);

        assertEquals("course/detail", viewName);
    }

    @Test
    void myCourses_shouldReturnCorrectView() {
        String viewName = controller.myCourses();
        assertEquals("course/my-courses", viewName);
    }
}
