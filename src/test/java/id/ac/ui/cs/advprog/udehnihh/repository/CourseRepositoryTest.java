package id.ac.ui.cs.advprog.udehnihh.repository;

@ExtendWith(MockitoExtension.class)
public class CourseRepositoryMockTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Course> query;

    @InjectMocks
    private CourseRepositoryImpl courseRepository;

    @Test
    public void testFindAll() {
        // Arrange
        List<Course> expectedCourses = Arrays.asList(
                new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>()),
                new Course(2L, "Web Development", "Learn Web", new User(), 75000.0, new ArrayList<>())
        );

        when(entityManager.createQuery("SELECT c FROM Course c", Course.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedCourses);

        // Act
        List<Course> result = courseRepository.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Java Basic", result.get(0).getName());
        assertEquals("Web Development", result.get(1).getName());
        verify(entityManager).createQuery("SELECT c FROM Course c", Course.class);
        verify(query).getResultList();
    }

    @Test
    public void testFindById_WhenCourseExists() {
        // Arrange
        Course expectedCourse = new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>());

        when(entityManager.find(Course.class, 1L)).thenReturn(expectedCourse);

        // Act
        Optional<Course> result = courseRepository.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Java Basic", result.get().getName());
        verify(entityManager).find(Course.class, 1L);
    }

    @Test
    public void testFindById_WhenCourseDoesNotExist() {
        // Arrange
        when(entityManager.find(Course.class, 1L)).thenReturn(null);

        // Act
        Optional<Course> result = courseRepository.findById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(entityManager).find(Course.class, 1L);
    }

    @Test
    public void testFindByName() {
        // Arrange
        List<Course> expectedCourses = Arrays.asList(
                new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>())
        );

        when(entityManager.createQuery("SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(:name)", Course.class))
                .thenReturn(query);
        when(query.setParameter("name", "%java%")).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedCourses);

        // Act
        List<Course> result = courseRepository.findByName("java");

        // Assert
        assertEquals(1, result.size());
        assertEquals("Java Basic", result.get(0).getName());
        verify(entityManager).createQuery("SELECT c FROM Course c WHERE LOWER(c.name) LIKE LOWER(:name)", Course.class);
        verify(query).setParameter("name", "%java%");
        verify(query).getResultList();
    }

    @Test
    public void testFindByPriceRange() {
        // Arrange
        List<Course> expectedCourses = Arrays.asList(
                new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>())
        );

        when(entityManager.createQuery("SELECT c FROM Course c WHERE c.price BETWEEN :minPrice AND :maxPrice", Course.class))
                .thenReturn(query);
        when(query.setParameter("minPrice", 40000.0)).thenReturn(query);
        when(query.setParameter("maxPrice", 60000.0)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedCourses);

        // Act
        List<Course> result = courseRepository.findByPriceRange(40000.0, 60000.0);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Java Basic", result.get(0).getName());
        assertEquals(50000.0, result.get(0).getPrice());
        verify(entityManager).createQuery("SELECT c FROM Course c WHERE c.price BETWEEN :minPrice AND :maxPrice", Course.class);
        verify(query).setParameter("minPrice", 40000.0);
        verify(query).setParameter("maxPrice", 60000.0);
        verify(query).getResultList();
    }

    @Test
    public void testSave() {
        // Arrange
        Course courseToBeSaved = new Course(null, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>());
        Course savedCourse = new Course(1L, "Java Basic", "Learn Java", new User(), 50000.0, new ArrayList<>());

        doAnswer(invocation -> {
            Course course = invocation.getArgument(0);
            ReflectionTestUtils.setField(course, "id", 1L);
            return null;
        }).when(entityManager).persist(any(Course.class));

        // Act
        Course result = courseRepository.save(courseToBeSaved);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Java Basic", result.getName());
        verify(entityManager).persist(courseToBeSaved);
    }
}
