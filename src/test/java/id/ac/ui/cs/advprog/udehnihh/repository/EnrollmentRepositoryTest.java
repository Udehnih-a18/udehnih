package id.ac.ui.cs.advprog.udehnihh.repository;

@ExtendWith(MockitoExtension.class)
public class EnrollmentRepositoryMockTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Enrollment> query;

    @InjectMocks
    private EnrollmentRepositoryImpl enrollmentRepository;

    @Test
    public void testFindByStudent() {
        // Arrange
        User student = new User(1L, "student@example.com", "Student", "password");
        List<Enrollment> expectedEnrollments = Arrays.asList(
                new Enrollment(1L, student, new Course(), LocalDateTime.now(), "PAID"),
                new Enrollment(2L, student, new Course(), LocalDateTime.now(), "PENDING")
        );

        when(entityManager.createQuery("SELECT e FROM Enrollment e WHERE e.student = :student", Enrollment.class))
                .thenReturn(query);
        when(query.setParameter("student", student)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedEnrollments);

        // Act
        List<Enrollment> result = enrollmentRepository.findByStudent(student);

        // Assert
        assertEquals(2, result.size());
        assertEquals(student, result.get(0).getStudent());
        assertEquals(student, result.get(1).getStudent());
        verify(entityManager).createQuery("SELECT e FROM Enrollment e WHERE e.student = :student", Enrollment.class);
        verify(query).setParameter("student", student);
        verify(query).getResultList();
    }

    @Test
    public void testFindByStudentAndCourse_WhenEnrollmentExists() {
        // Arrange
        User student = new User(1L, "student@example.com", "Student", "password");
        Course course = new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>());
        Enrollment expectedEnrollment = new Enrollment(1L, student, course, LocalDateTime.now(), "PAID");

        when(entityManager.createQuery(
                "SELECT e FROM Enrollment e WHERE e.student = :student AND e.course = :course",
                Enrollment.class))
                .thenReturn(query);
        when(query.setParameter("student", student)).thenReturn(query);
        when(query.setParameter("course", course)).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(expectedEnrollment));

        // Act
        Optional<Enrollment> result = enrollmentRepository.findByStudentAndCourse(student, course);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(student, result.get().getStudent());
        assertEquals(course, result.get().getCourse());
        verify(entityManager).createQuery(
                "SELECT e FROM Enrollment e WHERE e.student = :student AND e.course = :course",
                Enrollment.class);
        verify(query).setParameter("student", student);
        verify(query).setParameter("course", course);
        verify(query).getResultList();
    }

    @Test
    public void testFindByStudentAndCourse_WhenEnrollmentDoesNotExist() {
        // Arrange
        User student = new User(1L, "student@example.com", "Student", "password");
        Course course = new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>());

        when(entityManager.createQuery(
                "SELECT e FROM Enrollment e WHERE e.student = :student AND e.course = :course",
                Enrollment.class))
                .thenReturn(query);
        when(query.setParameter("student", student)).thenReturn(query);
        when(query.setParameter("course", course)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        // Act
        Optional<Enrollment> result = enrollmentRepository.findByStudentAndCourse(student, course);

        // Assert
        assertFalse(result.isPresent());
        verify(entityManager).createQuery(
                "SELECT e FROM Enrollment e WHERE e.student = :student AND e.course = :course",
                Enrollment.class);
        verify(query).setParameter("student", student);
        verify(query).setParameter("course", course);
        verify(query).getResultList();
    }

    @Test
    public void testSave() {
        // Arrange
        User student = new User(1L, "student@example.com", "Student", "password");
        Course course = new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>());
        Enrollment enrollmentToBeSaved = new Enrollment(null, student, course, LocalDateTime.now(), "PENDING");

        doAnswer(invocation -> {
            Enrollment enrollment = invocation.getArgument(0);
            ReflectionTestUtils.setField(enrollment, "id", 1L);
            return null;
        }).when(entityManager).persist(any(Enrollment.class));

        // Act
        Enrollment result = enrollmentRepository.save(enrollmentToBeSaved);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals(student, result.getStudent());
        assertEquals(course, result.getCourse());
        assertEquals("PENDING", result.getPaymentStatus());
        verify(entityManager).persist(enrollmentToBeSaved);
    }

    @Test
    public void testDelete() {
        // Arrange
        Enrollment enrollment = new Enrollment(1L, new User(), new Course(), LocalDateTime.now(), "PAID");

        // Act
        enrollmentRepository.delete(enrollment);

        // Assert
        verify(entityManager).remove(enrollment);
    }
}