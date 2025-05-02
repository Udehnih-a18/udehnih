package id.ac.ui.cs.advprog.udehnihh.service;

import id.ac.ui.cs.advprog.udehnihh.dto.*;
import id.ac.ui.cs.advprog.udehnihh.mapper.CourseMapper;
import id.ac.ui.cs.advprog.udehnihh.repository.CbCourseRepository;
import id.ac.ui.cs.advprog.udehnihh.spec.CourseSpecification;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CbCourseService {

    private final CbCourseRepository courseRepo;

    public List<CourseSummaryDto> search(String q, Double min, Double max) {
        Specification<Course> spec = Specification.where(
                CourseSpecification.nameContains(q)
        ).and(CourseSpecification.priceBetween(min, max));

        return courseRepo.findAll(spec).stream()
                .map(CourseMapper::toSummary)
                .toList();
    }

    public CourseDetailDto getDetail(UUID id) {
        Course c = courseRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        return CourseMapper.toDetail(c);
    }
}
