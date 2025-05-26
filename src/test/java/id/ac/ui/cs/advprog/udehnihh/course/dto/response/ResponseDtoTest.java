package id.ac.ui.cs.advprog.udehnihh.course.dto.response;

import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import id.ac.ui.cs.advprog.udehnihh.course.model.Course.CourseStatus;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication.ApplicationStatus;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseDtoTest {

    @Test
    public void testMapToCourseResponse() {
        UUID tutorId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();

        User tutor = new User();
        tutor.setId(tutorId);
        tutor.setFullName("Sultan Ibnu");

        Course course = new Course();
        course.setId(courseId);
        course.setName("Pemrograman Lanjut");
        course.setDescription("Belajar Spring Boot");
        course.setPrice(new BigDecimal("150000.0"));
        course.setTutor(tutor);
        course.setStatus(CourseStatus.APPROVED);

        CourseResponse response = CourseResponse.mapToCourseResponse(course);

        assertEquals(courseId, response.getId());
        assertEquals("Pemrograman Lanjut", response.getName());
        assertEquals("Belajar Spring Boot", response.getDescription());
        assertEquals(new BigDecimal("150000.0"), response.getPrice());
        assertEquals(tutorId, response.getTutorId());
        assertEquals("Sultan Ibnu", response.getTutorName());
        assertEquals("APPROVED", response.getStatus());
    }

    @Test
    public void testTutorApplicationResponseGetterSetter() {
        UUID id = UUID.randomUUID();
        UUID applicantId = UUID.randomUUID();
        String applicantName = "Ibnu";
        String motivation = "Saya ingin mengajar";
        String experience = "2 tahun ngoding";
        ApplicationStatus status = ApplicationStatus.PENDING;
        LocalDateTime createdAt = LocalDateTime.now();

        TutorApplicationResponse response = new TutorApplicationResponse();
        response.setId(id);
        response.setApplicantId(applicantId);
        response.setApplicantName(applicantName);
        response.setMotivation(motivation);
        response.setExperience(experience);
        response.setStatus(status);
        response.setCreatedAt(createdAt);

        assertEquals(id, response.getId());
        assertEquals(applicantId, response.getApplicantId());
        assertEquals(applicantName, response.getApplicantName());
        assertEquals(motivation, response.getMotivation());
        assertEquals(experience, response.getExperience());
        assertEquals(status, response.getStatus());
        assertEquals(createdAt, response.getCreatedAt());
    }
}
