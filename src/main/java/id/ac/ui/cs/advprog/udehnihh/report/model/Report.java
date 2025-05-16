package id.ac.ui.cs.advprog.udehnihh.report.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reports")
public class Report {

    @Id
    private String idReport;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String author;
    private String title;
    private String description;

    public Report(String createdBy, String author, String title, String description) {
        this.idReport = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = createdBy;
        this.author = author;
        this.title = title;
        this.description = description;
    }
}
