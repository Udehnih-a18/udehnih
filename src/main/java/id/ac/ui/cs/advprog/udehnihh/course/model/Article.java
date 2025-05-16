package id.ac.ui.cs.advprog.udehnihh.course.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "content_url", nullable = false)
    private String contentUrl;

    @Column(name = "content_description", nullable = false)
    private String contentDescription;

    @Column(name = "content_title", nullable = false)
    private String contentTitle;

    @Column(name = "content_text", nullable = false, length = 1000)
    private String contentText;
    
    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;
}