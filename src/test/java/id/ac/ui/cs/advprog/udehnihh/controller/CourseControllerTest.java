package id.ac.ui.cs.advprog.udehnihh.controller;

import id.ac.ui.cs.advprog.udehnihh.model.Course;
import id.ac.ui.cs.advprog.udehnihh.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    public void testListCourses() throws Exception {
        // Arrange
        List<Course> courses = Arrays.asList(
                new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>()),
                new Course(2L, "Web Development", "Learn HTML, CSS, JS", new User(), 75000.0, new ArrayList<>())
        );
        when(courseService.getAllCourses()).thenReturn(courses);

        // Act & Assert
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("course-list"))
                .andExpect(model().attribute("courses", hasSize(2)));
    }

    @Test
    public void testCourseDetail() throws Exception {
        // Arrange
        Course course = new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>());
        when(courseService.getCourseById(1L)).thenReturn(Optional.of(course));

        // Act & Assert
        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("course-detail"))
                .andExpect(model().attributeExists("course"));
    }

    @Test
    public void testCourseDetail_NotFound() throws Exception {
        // Arrange
        when(courseService.getCourseById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchCourses() throws Exception {
        // Arrange
        List<Course> courses = Arrays.asList(
                new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>())
        );
        when(courseService.searchCourses("Java")).thenReturn(courses);

        // Act & Assert
        mockMvc.perform(get("/courses/search").param("query", "Java"))
                .andExpect(status().isOk())
                .andExpect(view().name("course-list"))
                .andExpect(model().attribute("courses", hasSize(1)));
    }
}
