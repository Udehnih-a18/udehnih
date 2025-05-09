package id.ac.ui.cs.advprog.udehnihh.dto;

import java.util.UUID;

public record CourseSummaryDto(
        UUID id,
        String name,
        String tutorName,
        Double priceRp
) {}
