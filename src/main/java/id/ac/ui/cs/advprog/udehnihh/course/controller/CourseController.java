package id.ac.ui.cs.advprog.udehnihh.course.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import id.ac.ui.cs.advprog.udehnihh.authentication.service.JwtService;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.response.CourseResponse;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course.CourseStatus;
import id.ac.ui.cs.advprog.udehnihh.course.service.CourseService;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CourseCreationRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseCreationRepository courseRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @GetMapping("/all")
    public List<CourseResponse> getAllApprovedCourses() {
        return courseRepository.findByStatus(CourseStatus.APPROVED)
            .stream()
            .map(CourseResponse::mapToCourseResponse)
            .toList();
    }

    @PostMapping("/create")
    public ResponseEntity<CourseResponse> createFullCourse(@RequestBody CourseRequest request) {
        CourseResponse response = courseService.createFullCourse(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lists")
    public ResponseEntity<List<CourseResponse>> getCoursesByTutor(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = authHeader.substring(7);

        String email;
        try {
            email = jwtService.getEmailFromToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<User> tutorOpt = userRepository.findByEmail(email);
        if (tutorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User tutor = tutorOpt.get();
        List<CourseResponse> responses = courseService.getCoursesByTutor(tutor.getId());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getCourseDetail(@PathVariable("courseId") UUID courseId) {
        return courseService.getCourseById(courseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable("courseId") UUID courseId,
                                                       @RequestBody CourseRequest request) {
        CourseResponse updated = courseService.updateCourse(courseId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") UUID courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
