package id.ac.ui.cs.advprog.udehnihh.course.mapper;

import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseDetailDto;
import id.ac.ui.cs.advprog.udehnihh.course.dto.CourseSummaryDto;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.model.*;
import id.ac.ui.cs.advprog.udehnihh.dto.*;

import java.util.stream.Collectors;

public final class CourseMapper {

    public static CourseSummaryDto toSummary(Course c) {
        return new CourseSummaryDto(
                c.getId(),
                c.getName(),
                c.getTutor().getFullName(),
                c.getPrice()
        );
    }

    public static CourseDetailDto toDetail(Course c) {
        return new CourseDetailDto(
                c.getId(),
                c.getName(),
                c.getDescription(),
                c.getTutor().getFullName(),
                c.getPrice(),
                c.getSections().stream()
                        .map(s -> new CourseDetailDto.SectionDto(
                                s.getName(),
                                s.getDescription(),
                                s.getSectionContents().stream()
                                  .map(a -> new CourseDetailDto.ArticleDto(
                                          a.getContentType(),
                                          a.getContentUrl(),
                                          a.getContentDescription(),
                                          a.getContentTitle(),
                                          a.getContentText()
                                  )).toList()
                        )).collect(Collectors.toList())
        );
    }
}
