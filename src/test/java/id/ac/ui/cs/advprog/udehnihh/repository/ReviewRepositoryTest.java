package id.ac.ui.cs.advprog.udehnihh.repository;

//import id.ac.ui.cs.advprog.udehnihh.repository.ReviewRepository;
import id.ac.ui.cs.advprog.udehnihh.model.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryTest {
    ReviewRepository reviewRepository;
    List <Review> reviews;

    @BeforeEach
    void setUp() {
        reviewRepository = new ReviewRepository();

        reviews = new ArrayList<>();
        Review review1 = new Review("c7dc8511-d196-4a30-94a3-800d2e1c71ec",
                "c175aa39-d000-4516-987e-18957f81ea59", "41df544b-8c54-40ac-9ac2-ff9ce312f92d",
                5, "Keren banget!");
        reviews.add(review1);
        Review review2 = new Review("5409421c-269f-46f8-97ea-082eb0ade378",
                "7407610b-bce3-4517-b5da-44a668b009f2", "06edd2fa-d8fd-4bb1-b38c-9454bd6fc504",
                1, "Tidak sesuai deskripsi.");
        reviews.add(review2);
        Review review3 = new Review("486027cd-4dbf-49b5-b384-165f92057386",
                "89a212d1-8b9b-4a18-947f-204ea6c0959f", "9374f9c3-d548-4dc7-b348-0d598653c0cf",
                3, "Lumayan membantu.");
        reviews.add(review3);
    }

    @Test
    void testSaveCreate() {
        Review review = reviews.get(1);
        Review result = reviewRepository.save(review);

        Review findResult = reviewRepository.findById(reviews.get(1).getId());
        assertEquals(reviews.getId(), result.getId());
        assertEquals(reviews.getId(), findResult.getId());
        assertEquals(reviews.getStudentId(), findResult.getStudentId());
        assertEquals(reviews.getCourseId(), findResult.getCourseId());
        assertEquals(reviews.getRating(), findResult.getRating());
        assertEquals(reviews.getReview(), findResult.getReview());
    }

    @Test
    void testSaveUpdate() {
        Review review = reviews.get(1);
        reviewRepository.save(review);

        Review newReview = new Review(review.getId(), review.getStudentId(),
                review.getCourseId(), review.getRating(), review.getReview());
        Review result = reviewRepository.save(newReview);

        Review findResult = reviewRepository.findById(reviews.get(1).getId());
        assertEquals(reviews.getId(), result.getId());
        assertEquals(reviews.getId(), findResult.getId());
        assertEquals(reviews.getStudentId(), findResult.getStudentId());
        assertEquals(reviews.getCourseId(), findResult.getCourseId());
        assertEquals(reviews.getRating(), findResult.getRating());
        assertEquals(reviews.getReview(), findResult.getReview());
    }

    @Test
    void testFindByIdIfFound() {
        for (Review review : reviews) {
            reviewRepository.save(review);
        }

        Review findResult = reviewRepository.findById(reviews.get(1).getId());
        assertEquals(reviews.get(1).getId(), findResult.getId());
        assertEquals(reviews.get(1).getStudentId(), findResult.getStudentId());
        assertEquals(reviews.get(1).getCourseId(), findResult.getCourseId());
        assertEquals(reviews.get(1).getRating(), findResult.getRating());
        assertEquals(reviews.get(1).getReview(), findResult.getReview());
    }
}
