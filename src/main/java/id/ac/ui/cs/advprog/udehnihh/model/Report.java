package id.ac.ui.cs.advprog.udehnihh.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Report {
    String id;
    @Setter
    String title;
    @Setter
    String description;
    String author;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public Report(String id, String title, String description, String author){
    }
}
