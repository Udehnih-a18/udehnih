package id.ac.ui.cs.advprog.udehnihh.dto;

import java.util.List;
import java.util.UUID;

public record CourseDetailDto(
        UUID id,
        String name,
        String description,
        String tutorName,
        Double priceRp,
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