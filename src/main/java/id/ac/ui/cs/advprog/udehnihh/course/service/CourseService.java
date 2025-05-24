package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.course.dto.request.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.response.CourseResponse;
import java.util.*;

public interface CourseService {

    CourseResponse createFullCourse(CourseRequest request);
    List<CourseResponse> getCoursesByTutor(UUID tutorId);
    Optional<CourseResponse> getCourseById(UUID courseId);
    CourseResponse updateCourse(UUID courseId, CourseRequest request);
    void deleteCourse(UUID courseId);
} 
