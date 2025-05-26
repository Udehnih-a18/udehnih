package id.ac.ui.cs.advprog.udehnihh.course.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void testCourseDetailDtoAccessors() {
        UUID id = UUID.randomUUID();
        String name = "Basic Java";
        String description = "Learn Java basics";
        String tutorName = "Jane Doe";
        BigDecimal price = new BigDecimal("250000.0");

        CourseDetailDto.ArticleDto article = new CourseDetailDto.ArticleDto(
                "video",
                "https://example.com/video",
                "Introduction video",
                "Intro",
                "This is an intro video"
        );

        CourseDetailDto.SectionDto section = new CourseDetailDto.SectionDto(
                "Introduction",
                "Getting started",
                List.of(article)
        );

        CourseDetailDto dto = new CourseDetailDto(
                id,
                name,
                description,
                tutorName,
                price,
                List.of(section)
        );

        // Validate accessors (getters)
        assertEquals(id, dto.id());
        assertEquals(name, dto.name());
        assertEquals(description, dto.description());
        assertEquals(tutorName, dto.tutorName());
        assertEquals(price, dto.priceRp());

        assertEquals(1, dto.sections().size());
        CourseDetailDto.SectionDto retrievedSection = dto.sections().get(0);
        assertEquals("Introduction", retrievedSection.title());
        assertEquals("Getting started", retrievedSection.description());

        CourseDetailDto.ArticleDto retrievedArticle = retrievedSection.articles().get(0);
        assertEquals("video", retrievedArticle.type());
        assertEquals("https://example.com/video", retrievedArticle.url());
        assertEquals("Introduction video", retrievedArticle.description());
        assertEquals("Intro", retrievedArticle.title());
        assertEquals("This is an intro video", retrievedArticle.text());
    }
}