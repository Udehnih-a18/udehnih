package id.ac.ui.cs.advprog.udehnihh.course.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication.ApplicationStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class TutorApplicationResponse {
    private UUID id;
    private UUID applicantId;
    private String applicantName;
    private String motivation;
    private String experience;
    private ApplicationStatus status;
    private LocalDateTime createdAt;
}