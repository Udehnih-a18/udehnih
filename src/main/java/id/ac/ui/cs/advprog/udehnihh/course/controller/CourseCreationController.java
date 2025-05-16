package id.ac.ui.cs.advprog.udehnihh.course.controller;

import id.ac.ui.cs.advprog.udehnihh.course.dto.ArticleRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.SectionRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseRequest;
import id.ac.ui.cs.advprog.udehnihh.course.model.Article;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.course.model.Section;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.repository.ArticleRepository;
import id.ac.ui.cs.advprog.udehnihh.course.repository.CourseCreationRepository;
import id.ac.ui.cs.advprog.udehnihh.course.repository.SectionRepository;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseCreationController {

    private final CourseCreationRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequest request) {
        Optional<User> tutor = userRepository.findById(request.getTutorId());
        if (tutor.isEmpty()) return ResponseEntity.badRequest().build();

        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setTutor(tutor.get());

        return ResponseEntity.ok(courseRepository.save(course));
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<Course>> getCoursesByTutor(@PathVariable UUID tutorId) {
        return ResponseEntity.ok(courseRepository.findByTutorId(tutorId));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable UUID courseId, @RequestBody CourseRequest request) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) return ResponseEntity.notFound().build();

        Course course = courseOpt.get();
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setName(request.getName());

        return ResponseEntity.ok(courseRepository.save(course));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID courseId) {
        if (!courseRepository.existsById(courseId)) return ResponseEntity.notFound().build();
        courseRepository.deleteById(courseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseDetail(@PathVariable UUID courseId) {
        return courseRepository.findById(courseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{courseId}/sections")
    public ResponseEntity<Section> createSection(
            @PathVariable UUID courseId,
            @RequestBody SectionRequest request) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isEmpty()) return ResponseEntity.notFound().build();

        Section section = new Section();
        section.setName(request.getName());
        section.setDescription(request.getDescription());
        section.setCourse(courseOpt.get());

        return ResponseEntity.ok(sectionRepository.save(section));
    }

    @GetMapping("/{courseId}/sections")
    public ResponseEntity<List<Section>> getSections(@PathVariable UUID courseId) {
        return ResponseEntity.ok(sectionRepository.findByCourseId(courseId));
    }

    @DeleteMapping("/sections/{sectionId}")
    public ResponseEntity<Void> deleteSection(@PathVariable UUID sectionId) {
        if (!sectionRepository.existsById(sectionId)) return ResponseEntity.notFound().build();
        sectionRepository.deleteById(sectionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sections/{sectionId}/articles")
    public ResponseEntity<Article> createArticle(
            @PathVariable UUID sectionId,
            @RequestBody ArticleRequest request) {
        Optional<Section> sectionOpt = sectionRepository.findById(sectionId);
        if (sectionOpt.isEmpty()) return ResponseEntity.notFound().build();

        Article article = new Article();
        article.setContentTitle(request.getContentTitle());
        article.setContentType(request.getContentType());
        article.setContentUrl(request.getContentUrl());
        article.setContentDescription(request.getContentDescription());
        article.setContentText(request.getContentText());

        return ResponseEntity.ok(articleRepository.save(article));
    }

    @GetMapping("/sections/{sectionId}/articles")
    public ResponseEntity<List<Article>> getArticles(@PathVariable UUID sectionId) {
        return ResponseEntity.ok(articleRepository.findBySectionId(sectionId));
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable UUID articleId) {
        if (!articleRepository.existsById(articleId)) return ResponseEntity.notFound().build();
        articleRepository.deleteById(articleId);
        return ResponseEntity.noContent().build();
    }
}
