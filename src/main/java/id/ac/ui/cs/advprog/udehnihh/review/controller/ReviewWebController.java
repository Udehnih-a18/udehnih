package id.ac.ui.cs.advprog.udehnihh.review.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReviewWebController {

    @GetMapping("/my-reviews")
    public String myReviewsPage() {
        return "review/my-reviews";
    }

    @GetMapping("/courses/{courseId}/review")
    public String createReviewPage(@PathVariable String courseId) {
        return "review/create-review";
    }
}
