package id.ac.ui.cs.advprog.udehnihh.course.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CourseRequest {
    private String name;
    private String description;
    private Double price;
    private UUID tutorId;
    private List<SectionRequest> sections;
}
