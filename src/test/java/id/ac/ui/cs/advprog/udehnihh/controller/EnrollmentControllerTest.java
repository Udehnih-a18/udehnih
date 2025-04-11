package id.ac.ui.cs.advprog.udehnihh.controller;

@WebMvcTest(EnrollmentController.class)
public class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentService enrollmentService;

    @Test
    @WithMockUser(username = "student@example.com")
    public void testEnrollCourse() throws Exception {
        // Arrange
        User student = new User(1L, "student@example.com", "Student", "password");
        doNothing().when(enrollmentService).enrollStudentToCourse(any(User.class), eq(1L));

        // Act & Assert
        mockMvc.perform(post("/enrollments/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courses/1"));
    }

    @Test
    @WithMockUser(username = "student@example.com")
    public void testMyCourses() throws Exception {
        // Arrange
        User student = new User(1L, "student@example.com", "Student", "password");
        List<Enrollment> enrollments = Arrays.asList(
                new Enrollment(1L, student, new Course(), LocalDateTime.now(), "PAID"),
                new Enrollment(2L, student, new Course(), LocalDateTime.now(), "PENDING")
        );

        when(enrollmentService.getEnrollmentsByStudent(any(User.class))).thenReturn(enrollments);

        // Act & Assert
        mockMvc.perform(get("/enrollments/my-courses"))
                .andExpect(status().isOk())
                .andExpect(view().name("my-courses"))
                .andExpect(model().attribute("enrollments", hasSize(2)));
    }
}