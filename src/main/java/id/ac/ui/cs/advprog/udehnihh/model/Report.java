package id.ac.ui.cs.advprog.udehnihh.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Report {
    private final String idReport;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String createdBy;
    private final String author;

    @Setter
    private String title;

    @Setter
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
