package id.ac.ui.cs.advprog.udehnihh.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReviewTest {

    @Test
    void testCreateMaxRatingAndReview() {
        Review review = new Review("c7dc8511-d196-4a30-94a3-800d2e1c71ec",
                "c175aa39-d000-4516-987e-18957f81ea59", "41df544b-8c54-40ac-9ac2-ff9ce312f92d",
                5, "Keren banget!");
    }

    @Test
    void testCreateMinRatingAndReview() {
        Review review = new Review("c7dc8511-d196-4a30-94a3-800d2e1c71ec",
                "c175aa39-d000-4516-987e-18957f81ea59", "41df544b-8c54-40ac-9ac2-ff9ce312f92d",
                1, "Tidak sesuai deskripsi.");
    }

    @Test
    void testCreateRatingNoReview() {
        Review review = new Review("c7dc8511-d196-4a30-94a3-800d2e1c71ec",
                "c175aa39-d000-4516-987e-18957f81ea59", "41df544b-8c54-40ac-9ac2-ff9ce312f92d",
                3);
    }

    @Test
    void testEmptyRatingAndReview() {
        assertThrows(IllegalArgumentException.class, () -> {
            Review review = new Review("c7dc8511-d196-4a30-94a3-800d2e1c71ec",
                    "c175aa39-d000-4516-987e-18957f81ea59", "41df544b-8c54-40ac-9ac2-ff9ce312f92d");
        });
    }

    @Test
    void testEmptyRatingWithReview() {
        assertThrows(IllegalArgumentException.class, () -> {
            Review review = new Review("c7dc8511-d196-4a30-94a3-800d2e1c71ec",
                    "c175aa39-d000-4516-987e-18957f81ea59", "41df544b-8c54-40ac-9ac2-ff9ce312f92d",
                    "Lumayan membantu.");
        });
    }
}
