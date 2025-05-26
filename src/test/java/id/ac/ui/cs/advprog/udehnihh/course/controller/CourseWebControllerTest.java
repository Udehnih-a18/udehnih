package id.ac.ui.cs.advprog.udehnihh.course.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseWebControllerTest {

    @InjectMocks
    private CourseWebController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showTutorRegisterPage_shouldReturnCorrectView() {
        String viewName = controller.showTutorRegisterPage();
        assertEquals("course/tutor_register", viewName);
    }

    @Test
    void showTutorApplicantPage_shouldReturnCorrectView() {
        String viewName = controller.showTutorApplicantPage();
        assertEquals("course/tutor_applicant", viewName);
    }

    @Test
    void showTutorCourseListPage_shouldReturnCorrectView() {
        String viewName = controller.showTutorCourseListPage();
        assertEquals("course/tutor_courses_list", viewName);
    }

    @Test
    void showTutorCourseDetailPage_shouldReturnCorrectView() {
        // Gunakan parameter yang sesuai, misal Long atau String, tergantung method kamu
        String viewName = controller.showTutorCourseDetailPage("123");
        assertEquals("course/tutor_course_detail", viewName);
    }

    @Test
    void showTutorCreateCoursePage_shouldReturnCorrectView() {
        String viewName = controller.showTutorCreateCoursePage();
        assertEquals("course/tutor_add_course", viewName);
    }
}
