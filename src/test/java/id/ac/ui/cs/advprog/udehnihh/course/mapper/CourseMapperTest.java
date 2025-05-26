package id.ac.ui.cs.advprog.udehnihh.course.mapper;

import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseDetailDto;
import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseSummaryDto;
import id.ac.ui.cs.advprog.udehnihh.course.model.Article;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.course.model.Section;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CourseMapperTest {

    @Test
    void testToSummary() {
        UUID id = UUID.randomUUID();
        Course course = new Course();
        course.setId(id);
        course.setName("Pemrograman Java");
        course.setPrice(new BigDecimal("150000.0"));

        User tutor = new User();
        tutor.setFullName("Dosen UI");
        course.setTutor(tutor);

        CourseSummaryDto summary = CourseMapper.toSummary(course);

        assertEquals(id, summary.id());
        assertEquals("Pemrograman Java", summary.name());
        assertEquals("Dosen UI", summary.tutorName());
        assertEquals(new BigDecimal("150000.0"), summary.priceRp());
    }

    @Test
    void testToDetail() {
        UUID id = UUID.randomUUID();

        Article article = new Article();
        article.setContentType("video");
        article.setContentUrl("http://example.com/video");
        article.setContentDescription("Deskripsi video");
        article.setContentTitle("Judul Video");
        article.setContentText("Isi konten video");

        Section section = new Section();
        section.setName("Section 1");
        section.setDescription("Deskripsi section");
        section.setSectionContents(List.of(article));

        Course course = new Course();
        course.setId(id);
        course.setName("Kursus Backend");
        course.setDescription("Belajar backend");
        course.setPrice(new BigDecimal("200000.0"));

        User tutor = new User();
        tutor.setFullName("Pak Budi");
        course.setTutor(tutor);

        course.setSections(List.of(section));

        CourseDetailDto detail = CourseMapper.toDetail(course);

        assertEquals(id, detail.id());
        assertEquals("Kursus Backend", detail.name());
        assertEquals("Belajar backend", detail.description());
        assertEquals("Pak Budi", detail.tutorName());
        assertEquals(new BigDecimal("200000.0"), detail.priceRp());

        assertEquals(1, detail.sections().size());
        CourseDetailDto.SectionDto s = detail.sections().get(0);
        assertEquals("Section 1", s.title());
        assertEquals("Deskripsi section", s.description());

        assertEquals(1, s.articles().size());
        CourseDetailDto.ArticleDto a = s.articles().get(0);
        assertEquals("video", a.type());
        assertEquals("http://example.com/video", a.url());
        assertEquals("Deskripsi video", a.description());
        assertEquals("Judul Video", a.title());
        assertEquals("Isi konten video", a.text());
    }
}
