package id.ac.ui.cs.advprog.udehnihh.course.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

import id.ac.ui.cs.advprog.udehnihh.course.model.Course;

@Getter
@Setter
public class CourseResponse {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private UUID tutorId;
    private String tutorName;
    private String status;

    public static CourseResponse mapToCourseResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setName(course.getName());
        response.setDescription(course.getDescription());
        response.setPrice(course.getPrice());
        response.setTutorId(course.getTutor().getId());
        response.setTutorName(course.getTutor().getFullName());
        response.setStatus(course.getStatus().name());
        return response;
    }
}