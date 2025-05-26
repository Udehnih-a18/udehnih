package id.ac.ui.cs.advprog.udehnihh.review.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewWebControllerTest {

    @InjectMocks
    private ReviewWebController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void myReviewsPage_shouldReturnCorrectView() {
        // When
        String viewName = controller.myReviewsPage();

        // Then
        assertEquals("review/my-reviews", viewName);
    }

    @Test
    void createReviewPage_shouldReturnCorrectView() {
        // Given
        String courseId = "some-course-id";

        // When
        String viewName = controller.createReviewPage(courseId);

        // Then
        assertEquals("review/create-review", viewName);
    }
}
