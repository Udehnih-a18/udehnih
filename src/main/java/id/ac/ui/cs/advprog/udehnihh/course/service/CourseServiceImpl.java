package id.ac.ui.cs.advprog.udehnihh.course.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.ArticleRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.SectionRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.response.CourseResponse;
import id.ac.ui.cs.advprog.udehnihh.course.model.Article;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.course.model.Section;
import id.ac.ui.cs.advprog.udehnihh.course.repository.ArticleRepository;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CourseCreationRepository;
import id.ac.ui.cs.advprog.udehnihh.course.repository.SectionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseCreationRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CourseResponse createFullCourse(CourseRequest request, UUID tutorId) {
        User tutor = userRepository.findById(tutorId)
                .orElseThrow(() -> new IllegalArgumentException("Tutor not found"));

        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setTutor(tutor);

        course = courseRepository.save(course);

        if (request.getSections() != null) {
            for (SectionRequest sectionReq : request.getSections()) {
                Section section = new Section();
                section.setName(sectionReq.getName());
                section.setDescription(sectionReq.getDescription());
                section.setCourse(course);
                section = sectionRepository.save(section);

                if (sectionReq.getArticles() != null) {
                    for (ArticleRequest articleReq : sectionReq.getArticles()) {
                        Article article = new Article();
                        article.setContentTitle(articleReq.getContentTitle());
                        article.setContentType(articleReq.getContentType());
                        article.setContentText(articleReq.getContentText());
                        article.setContentUrl(articleReq.getContentUrl());
                        article.setContentDescription(articleReq.getContentDescription());
                        article.setSection(section);
                        articleRepository.save(article);
                    }
                }
            }
        }

        return CourseResponse.mapToCourseResponse(course);
    }

    public List<CourseResponse> getCoursesByTutor(UUID tutorId) {
        List<Course> courses = courseRepository.findByTutorId(tutorId);
        return courses.stream()
                .map(this::mapToCourseResponse)
                .toList();
    }

    public Optional<CourseResponse> getCourseById(UUID courseId) {
        return courseRepository.findById(courseId)
                .map(this::mapToCourseResponse);
    }

    public CourseResponse updateCourse(UUID courseId, CourseRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());

        course = courseRepository.save(course);

        return CourseResponse.mapToCourseResponse(course);
    }

    public void deleteCourse(UUID courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new IllegalArgumentException("Course not found");
        }
        courseRepository.deleteById(courseId);
    }

    private CourseResponse mapToCourseResponse(Course course) {
        return CourseResponse.mapToCourseResponse(course);
    }
}
