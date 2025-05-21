package id.ac.ui.cs.advprog.udehnihh.course.dto;

import id.ac.ui.cs.advprog.udehnihh.course.enums.CourseStatus;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
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
    private CourseStatus courseStatus;
}
