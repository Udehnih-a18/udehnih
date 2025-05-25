package id.ac.ui.cs.advprog.udehnihh.dashboard.dto;

import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TutorDetailRequest {
    private UUID applicationId;
    private String fullName;
    private String email;
    private ApplicationStatus status;
    private LocalDateTime registrationDate;
    private String notes;
}
