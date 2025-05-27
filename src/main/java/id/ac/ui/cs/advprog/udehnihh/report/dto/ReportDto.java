package id.ac.ui.cs.advprog.udehnihh.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private String idReport;
    private String title;
    private String description;
    private String authorId;
    private String authorEmail;
    private String authorFullName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}