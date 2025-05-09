package id.ac.ui.cs.advprog.udehnihh.course.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CourseRequest {
    private String name;
    private String description;
    private Double price;
    private UUID tutorId;
}
