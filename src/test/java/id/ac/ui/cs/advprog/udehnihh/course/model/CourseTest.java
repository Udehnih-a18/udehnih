package id.ac.ui.cs.advprog.udehnihh.course.model;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.course.model.Article;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.course.model.Section;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseTest {

    @Test
    void testCourseGettersAndSetters() {
        Course course = new Course();

        UUID id = UUID.randomUUID();
        String name = "Advanced Programming";
        String description = "This course is about Java and Spring Boot.";
        Double price = 249_000.0;

        User tutor = new User();
        tutor.setEmail("tutor@example.com");
        tutor.setFullName("John Doe");

        course.setId(id);
        course.setName(name);
        course.setDescription(description);
        course.setPrice(price);
        course.setTutor(tutor);

        assertThat(course.getId()).isEqualTo(id);
        assertThat(course.getName()).isEqualTo(name);
        assertThat(course.getDescription()).isEqualTo(description);
        assertThat(course.getPrice()).isEqualTo(price);
        assertThat(course.getTutor()).isEqualTo(tutor);
        assertThat(course.getSections()).isEmpty();
    }

    @Test
    void testAddSectionToCourse() {
        Course course = new Course();
        Section section = new Section();
        section.setName("Introduction to Spring Boot");
        section.setCourse(course);

        course.getSections().add(section);

        assertThat(course.getSections()).hasSize(1);
        assertThat(course.getSections().get(0).getName()).isEqualTo("Introduction to Spring Boot");
        assertThat(course.getSections().get(0).getCourse()).isEqualTo(course);
    }

    @Test
    void testAddVariousTypesOfArticlesToSection() {
        Section section = new Section();
        section.setId(UUID.randomUUID());
        section.setName("Module 1: Basics");
        section.setDescription("Introduction to programming.");
        section.setSectionContents(new ArrayList<>());

        Article textArticle = new Article();
        textArticle.setId(UUID.randomUUID());
        textArticle.setContentType("text");
        textArticle.setContentTitle("Text Intro");
        textArticle.setContentDescription("Basic intro in text");
        textArticle.setContentText("This is plain text content.");
        textArticle.setContentUrl("-");

        Article imageArticle = new Article();
        imageArticle.setId(UUID.randomUUID());
        imageArticle.setContentType("image");
        imageArticle.setContentTitle("Diagram");
        imageArticle.setContentDescription("Architecture diagram");
        imageArticle.setContentText("Diagram showing architecture.");
        imageArticle.setContentUrl("https://example.com/image.jpg");

        Article linkArticle = new Article();
        linkArticle.setId(UUID.randomUUID());
        linkArticle.setContentType("link");
        linkArticle.setContentTitle("Documentation");
        linkArticle.setContentDescription("Official docs");
        linkArticle.setContentText("Refer to the official docs.");
        linkArticle.setContentUrl("https://example.com/docs");

        section.getSectionContents().add(textArticle);
        section.getSectionContents().add(imageArticle);
        section.getSectionContents().add(linkArticle);

        assertThat(section.getSectionContents()).hasSize(3);

        assertThat(section.getSectionContents()).anyMatch(a ->
                a.getContentType().equals("text") &&
                a.getContentText().contains("plain text")
        );

        assertThat(section.getSectionContents()).anyMatch(a ->
                a.getContentType().equals("image") &&
                a.getContentUrl().contains("image.jpg")
        );

        assertThat(section.getSectionContents()).anyMatch(a ->
                a.getContentType().equals("link") &&
                a.getContentUrl().contains("docs")
        );
    }
}
