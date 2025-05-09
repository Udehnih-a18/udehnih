package id.ac.ui.cs.advprog.udehnihh.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.dto.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import id.ac.ui.cs.advprog.udehnihh.repository.CourseCreationRepository;
import jakarta.transaction.Transactional;

@Service
public class CourseCreationService {

    private final CourseCreationRepository courseCreationRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseCreationService(CourseCreationRepository courseCreationRepository, UserRepository userRepository) {
        this.courseCreationRepository = courseCreationRepository;
        this.userRepository = userRepository;
    }

    public List<Course> getAllCourses() {
        return courseCreationRepository.findAll();
    }

    public Course getCourseById(UUID id) {
        return courseCreationRepository.findById(id).orElse(null);
    }

    @Transactional
    public Course createCourse(CourseRequest courseRequest) {
        Optional<User> tutor = userRepository.findById(courseRequest.getTutorId());
        
        if (tutor.isEmpty()) {
            throw new IllegalArgumentException("Tutor not found");
        }
        
        Course course = new Course();
        course.setName(courseRequest.getName());
        course.setDescription(courseRequest.getDescription());
        course.setPrice(courseRequest.getPrice());
        course.setTutor(tutor.get());

        return courseCreationRepository.save(course);
    }

    public void deleteCourse(UUID id) {
        courseCreationRepository.deleteById(id);
    }
}
