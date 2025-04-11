package id.ac.ui.cs.advprog.udehnihh.model;

public class Review {
    String id;
    String studentId;
    String courseId;
    int rating;
    String review;

    public Review(String id, String studentId, String courseId, int rating) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.rating = rating;

        if ((1 <= rating) && (rating <= 5)) {
            this.rating = rating;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public Review(String id, String studentId, String courseId, int rating, String review) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.rating = rating;
        this.review = review;

        if ((1 <= rating) && (rating <= 5)) {
            this.rating = rating;
        }
        else {
            throw new IllegalArgumentException();
        }

        if (review.isEmpty()) {
            throw new IllegalArgumentException();
        }
        else {
            this.review = review;
        }
    }
}
