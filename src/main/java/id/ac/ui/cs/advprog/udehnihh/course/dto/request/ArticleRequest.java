package id.ac.ui.cs.advprog.udehnihh.course.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRequest {
    private String contentType;
    private String contentUrl;
    private String contentDescription;
    private String contentTitle;
    private String contentText;
}
