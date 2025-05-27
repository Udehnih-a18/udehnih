package id.ac.ui.cs.advprog.udehnihh.course.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomePageControllerTest {

    @InjectMocks
    private HomePageController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void homePage_shouldReturnHomePageView() {
        String viewName = controller.homePage();
        assertEquals("HomePage", viewName);
    }

    @Test
    void home_shouldReturnRedirectCourses() {
        String viewName = controller.home();
        assertEquals("redirect:/courses", viewName);
    }
}