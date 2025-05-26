package id.ac.ui.cs.advprog.udehnihh.course.dto.request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RequestDtoTest {

    @Test
    void testArticleRequestSettersAndGetters() {
        ArticleRequest article = new ArticleRequest();
        article.setContentType("video");
        article.setContentUrl("https://example.com/video.mp4");
        article.setContentDescription("Intro video");
        article.setContentTitle("Introduction");
        article.setContentText("Welcome to the course");

        assertEquals("video", article.getContentType());
        assertEquals("https://example.com/video.mp4", article.getContentUrl());
        assertEquals("Intro video", article.getContentDescription());
        assertEquals("Introduction", article.getContentTitle());
        assertEquals("Welcome to the course", article.getContentText());
    }

    @Test
    void testSectionRequestSettersAndGetters() {
        ArticleRequest article = new ArticleRequest();
        article.setContentTitle("Intro");

        SectionRequest section = new SectionRequest();
        section.setName("Getting Started");
        section.setDescription("Basics of the course");
        section.setArticles(List.of(article));

        assertEquals("Getting Started", section.getName());
        assertEquals("Basics of the course", section.getDescription());
        assertNotNull(section.getArticles());
        assertEquals(1, section.getArticles().size());
        assertEquals("Intro", section.getArticles().get(0).getContentTitle());
    }

    @Test
    void testCourseRequestSettersAndGetters() {
        SectionRequest section = new SectionRequest();
        section.setName("Module 1");

        CourseRequest course = new CourseRequest();
        course.setName("Fullstack Development");
        course.setDescription("Learn fullstack web development");
        course.setPrice(new BigDecimal("499000.0"));
        course.setSections(List.of(section));

        assertEquals("Fullstack Development", course.getName());
        assertEquals("Learn fullstack web development", course.getDescription());
        assertEquals(new BigDecimal("499000.0"), course.getPrice());
        assertNotNull(course.getSections());
        assertEquals("Module 1", course.getSections().get(0).getName());
    }

    @Test
    void testTutorApplicationRequestSettersAndGetters() {
        TutorApplicationRequest request = new TutorApplicationRequest();
        request.setMotivation("I want to teach.");
        request.setExperience("3 years teaching Java");

        assertEquals("I want to teach.", request.getMotivation());
        assertEquals("3 years teaching Java", request.getExperience());
    }
}
