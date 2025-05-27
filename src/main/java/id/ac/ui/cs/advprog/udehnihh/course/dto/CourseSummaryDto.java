package id.ac.ui.cs.advprog.udehnihh.course.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CourseSummaryDto(
        UUID id,
        String name,
        String tutorName,
        BigDecimal priceRp
) {}
