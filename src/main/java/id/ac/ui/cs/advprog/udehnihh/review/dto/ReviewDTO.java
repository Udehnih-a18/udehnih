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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    
    // Response fields
    private Long id;
    private Long studentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Request fields with validation
    @NotNull(groups = Create.class)
    private Long courseId;
    
    @NotNull
    @Min(1) @Max(5)
    private Integer rating;
    
    @Size(max = 1000)
    private String comment;
    
    // Validation groups
    public interface Create {}
    public interface Update {}
}