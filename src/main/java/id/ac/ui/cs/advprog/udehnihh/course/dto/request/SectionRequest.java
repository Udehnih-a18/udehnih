package id.ac.ui.cs.advprog.udehnihh.course.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionRequest {
    private String name;
    private String description;
    private List<ArticleRequest> articles;
}
