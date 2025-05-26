package id.ac.ui.cs.advprog.udehnihh.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.JwtService;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.response.CourseResponse;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course.CourseStatus;
import id.ac.ui.cs.advprog.udehnihh.course.service.CourseService;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CourseCreationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private JwtService jwtService;

    @MockBean
    private CourseCreationRepository courseRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;
    private UUID courseId;
    private CourseRequest courseRequest;
    private CourseResponse courseResponse;

    @BeforeEach
    void setUp() {
        User tutor = new User();
        tutor.setId(UUID.randomUUID());
        tutor.setFullName("Test Tutor");
        tutor.setEmail("tutor@example.com");
        tutor.setRole(Role.TUTOR);
        
        token = jwtService.generateToken(tutor.getEmail(), tutor.getRole().name(), tutor.getFullName());

        courseId = UUID.randomUUID();

        courseRequest = new CourseRequest();
        courseRequest.setName("Test Course");
        courseRequest.setDescription("Test Description");
        courseRequest.setPrice(100000.0);
        courseRequest.setTutorId(UUID.randomUUID());

        courseResponse = new CourseResponse();
        courseResponse.setId(courseId);
        courseResponse.setName(courseRequest.getName());
        courseResponse.setDescription(courseRequest.getDescription());
        courseResponse.setStatus(CourseStatus.PENDING.name());
        courseResponse.setPrice(courseRequest.getPrice());
        courseResponse.setTutorId(tutor.getId());
        courseResponse.setTutorName(tutor.getFullName());
    }

    @Test
    void testGetAllApprovedCourses() throws Exception {
        Mockito.when(courseRepository.findByStatus(CourseStatus.APPROVED))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/courses/all")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateFullCourse() throws Exception {
        Mockito.when(courseService.createFullCourse(any(CourseRequest.class)))
                .thenReturn(courseResponse);

        mockMvc.perform(post("/api/courses/create")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId.toString()))
                .andExpect(jsonPath("$.name").value(courseRequest.getName()))
                .andExpect(jsonPath("$.description").value(courseRequest.getDescription()));
    }

    @Test
    void testGetCourseDetailFound() throws Exception {
        Mockito.when(courseService.getCourseById(eq(courseId)))
                .thenReturn(Optional.of(courseResponse));

        mockMvc.perform(get("/api/courses/" + courseId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId.toString()))
                .andExpect(jsonPath("$.name").value(courseResponse.getName()));
    }

    @Test
    void testGetCourseDetailNotFound() throws Exception {
        Mockito.when(courseService.getCourseById(eq(courseId)))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/courses/" + courseId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateCourse() throws Exception {
        Mockito.when(courseService.updateCourse(eq(courseId), any(CourseRequest.class)))
                .thenReturn(courseResponse);

        mockMvc.perform(put("/api/courses/" + courseId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(courseId.toString()))
                .andExpect(jsonPath("$.name").value(courseRequest.getName()));
    }

    @Test
    void testDeleteCourse() throws Exception {
        Mockito.doNothing().when(courseService).deleteCourse(eq(courseId));

        mockMvc.perform(delete("/api/courses/" + courseId)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        Mockito.verify(courseService).deleteCourse(courseId);
    }

    @Test
    void testGetCoursesByTutor_Unauthorized_InvalidHeader() throws Exception {
        mockMvc.perform(get("/api/courses/lists")
                        .header("Authorization", "InvalidToken"))
                .andExpect(status().isUnauthorized());
    }

}
