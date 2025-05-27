package id.ac.ui.cs.advprog.udehnihh.course.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CourseRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private List<SectionRequest> sections;
}
