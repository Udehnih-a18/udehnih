package id.ac.ui.cs.advprog.udehnihh.course.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CourseDetailDto(
        UUID id,
        String name,
        String description,
        String tutorName,
        BigDecimal priceRp,
        List<SectionDto> sections
) {
    public record SectionDto(
            String title,
            String description,
            List<ArticleDto> articles
    ) {}
    public record ArticleDto(
            String type,
            String url,
            String description,
            String title,
            String text
    ) {}
}