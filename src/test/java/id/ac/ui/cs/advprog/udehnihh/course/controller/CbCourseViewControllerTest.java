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
        // When
        String viewName = controller.courseList();

        // Then
        assertEquals("course/list", viewName);
    }

    @Test
    void courseDetail_shouldReturnCorrectView() {
        // Given
        UUID courseId = UUID.randomUUID();

        // When
        String viewName = controller.courseDetail(courseId);

        // Then
        assertEquals("course/detail", viewName);
    }

    @Test
    void myCourses_shouldReturnCorrectView() {
        // When
        String viewName = controller.myCourses();

        // Then
        assertEquals("course/my-courses", viewName);
    }
}
