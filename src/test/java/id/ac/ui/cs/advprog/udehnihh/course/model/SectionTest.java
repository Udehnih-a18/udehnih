package id.ac.ui.cs.advprog.udehnihh.course.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SectionTest {

    @Test
    void testSectionSettersAndGetters() {
        UUID sectionId = UUID.randomUUID();
        String name = "Introduction";
        String description = "This is the introduction section.";
        Course course = new Course();
        course.setId(UUID.randomUUID());
        Article article1 = new Article();
        article1.setId(UUID.randomUUID());
        Article article2 = new Article();
        article2.setId(UUID.randomUUID());
        List<Article> articles = List.of(article1, article2);

        Section section = new Section();

        section.setId(sectionId);
        section.setName(name);
        section.setDescription(description);
        section.setCourse(course);
        section.setSectionContents(articles);

        assertEquals(sectionId, section.getId());
        assertEquals(name, section.getName());
        assertEquals(description, section.getDescription());
        assertEquals(course, section.getCourse());
        assertEquals(articles, section.getSectionContents());
    }

    @Test
    void testDefaultConstructor() {
        // Act
        Section section = new Section();

        assertNull(section.getId());
        assertNull(section.getName());
        assertNull(section.getDescription());
        assertNull(section.getCourse());
        assertNull(section.getSectionContents());
    }

    @Test
    void testAddAndRemoveArticles() {
        Section section = new Section();
        Article article1 = new Article();
        article1.setId(UUID.randomUUID());
        Article article2 = new Article();
        article2.setId(UUID.randomUUID());

        section.setSectionContents(new ArrayList<>(List.of(article1, article2)));

        section.getSectionContents().remove(article1);

        assertEquals(1, section.getSectionContents().size());
        assertTrue(section.getSectionContents().contains(article2));
        assertFalse(section.getSectionContents().contains(article1));
    }
}
