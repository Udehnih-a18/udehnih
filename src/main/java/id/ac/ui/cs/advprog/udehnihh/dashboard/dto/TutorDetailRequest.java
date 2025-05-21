package id.ac.ui.cs.advprog.udehnihh.dashboard.dto;

import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TutorDetailRequest {
    private String fullName;
    private String email;
    private String expertiseArea;
    private ApplicationStatus status;
    private LocalDateTime registrationDate;
    private LocalDateTime applicationDate;
    private String notes;
}
