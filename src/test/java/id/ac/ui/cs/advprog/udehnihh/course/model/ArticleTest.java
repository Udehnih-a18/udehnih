package id.ac.ui.cs.advprog.udehnihh.course.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ArticleTest {

    @Test
    void testArticleSettersAndGetters() {
        UUID articleId = UUID.randomUUID();
        String contentType = "Video";
        String contentUrl = "https://example.com/video";
        String contentDescription = "A sample video";
        String contentTitle = "Sample Video";
        String contentText = "This is a sample video description.";
        Section section = new Section();
        section.setId(UUID.randomUUID());

        Article article = new Article();

        article.setId(articleId);
        article.setContentType(contentType);
        article.setContentUrl(contentUrl);
        article.setContentDescription(contentDescription);
        article.setContentTitle(contentTitle);
        article.setContentText(contentText);
        article.setSection(section);

        // Assert
        assertEquals(articleId, article.getId());
        assertEquals(contentType, article.getContentType());
        assertEquals(contentUrl, article.getContentUrl());
        assertEquals(contentDescription, article.getContentDescription());
        assertEquals(contentTitle, article.getContentTitle());
        assertEquals(contentText, article.getContentText());
        assertEquals(section, article.getSection());
    }

    @Test
    void testDefaultConstructor() {
        Article article = new Article();

        assertNull(article.getId());
        assertNull(article.getContentType());
        assertNull(article.getContentUrl());
        assertNull(article.getContentDescription());
        assertNull(article.getContentTitle());
        assertNull(article.getContentText());
        assertNull(article.getSection());
    }

    @Test
    void testAllArgsConstructor() {
        UUID articleId = UUID.randomUUID();
        String contentType = "Video";
        String contentUrl = "https://example.com/video";
        String contentDescription = "A sample video";
        String contentTitle = "Sample Video";
        String contentText = "This is a sample video description.";
        Section section = new Section();
        section.setId(UUID.randomUUID());

        Article article = new Article(
            articleId,
            contentType,
            contentUrl,
            contentDescription,
            contentTitle,
            contentText,
            section
        );

        assertEquals(articleId, article.getId());
        assertEquals(contentType, article.getContentType());
        assertEquals(contentUrl, article.getContentUrl());
        assertEquals(contentDescription, article.getContentDescription());
        assertEquals(contentTitle, article.getContentTitle());
        assertEquals(contentText, article.getContentText());
        assertEquals(section, article.getSection());
    }
}
