package id.ac.ui.cs.advprog.udehnihh.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ReviewDTO {
    
    // Response fields
    private Long id;
    private UUID studentId;
    private String reviewerName;  // Add field for displaying reviewer name
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Request fields with validation
    @NotNull(groups = Create.class)
    private UUID courseId;  // Changed from Long to UUID
    
    @NotNull
    @Min(1) @Max(5)
    private Integer rating;
    
    @Size(max = 1000)
    private String comment;
    
    // Validation groups
    public interface Create {}
    public interface Update {}
}