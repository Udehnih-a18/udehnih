package id.ac.ui.cs.advprog.udehnihh.course.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import id.ac.ui.cs.advprog.udehnihh.course.dto.request.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.course.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.advprog.udehnihh.course.enums.CourseStatus;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;

import id.ac.ui.cs.advprog.udehnihh.course.repository.CourseCreationRepository;
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

    @Transactional
    public Course approveCourse(UUID courseId) {
        Course course = courseCreationRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getStatus() != CourseStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Course is not pending approval");
        }

        course.setStatus(CourseStatus.PUBLISHED);
        return courseCreationRepository.save(course);

    }

    @Transactional
    public Course rejectCourse(UUID courseId) {
        Course course = courseCreationRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        if (course.getStatus() != CourseStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Course is not pending approval");
        }

        course.setStatus(CourseStatus.REJECTED);
        return courseCreationRepository.save(course);

    }

    public List<CourseRequest> getAllCoursesDTO() {
        List<Course> courses = courseCreationRepository.findAll();
        return courses.stream()
                .map(course -> {
                    CourseRequest request = new CourseRequest();
                    request.setName(course.getName());
                    request.setDescription(course.getDescription());
                    request.setPrice(course.getPrice());
                    request.setTutorId(course.getTutor().getId());
                    request.setCourseStatus(course.getStatus());
                    return request;
                })
                .toList();
    }

    public void deleteCourse(UUID id) {
        courseCreationRepository.deleteById(id);
    }
}