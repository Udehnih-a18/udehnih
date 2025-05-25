package id.ac.ui.cs.advprog.udehnihh.review.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class ReviewDTOTest {
    
    private Validator validator;
    
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    @DisplayName("Should create valid DTO for request")
    void shouldCreateValidDTOForRequest() {
        // Given & When
        ReviewDTO dto = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment("Excellent course!")
            .build();
        
        // Then
        assertThat(dto.getCourseId()).isEqualTo(1L);
        assertThat(dto.getRating()).isEqualTo(5);
        assertThat(dto.getComment()).isEqualTo("Excellent course!");
    }
    
    @Test
    @DisplayName("Should create valid DTO for response")
    void shouldCreateValidDTOForResponse() {
        // Given & When
        LocalDateTime now = LocalDateTime.now();
        ReviewDTO dto = ReviewDTO.builder()
            .id(1L)
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Great!")
            .createdAt(now)
            .updatedAt(now)
            .build();
        
        // Then
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getStudentId()).isEqualTo(1L);
        assertThat(dto.getCourseId()).isEqualTo(1L);
        assertThat(dto.getRating()).isEqualTo(5);
        assertThat(dto.getComment()).isEqualTo("Great!");
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getUpdatedAt()).isEqualTo(now);
    }
    
    @Test
    @DisplayName("Should validate Create group - courseId required")
    void shouldValidateCreateGroupCourseIdRequired() {
        // Given
        ReviewDTO dto = ReviewDTO.builder()
            .rating(5)
            .comment("Missing courseId")
            .build();
        
        // When
        Set<ConstraintViolation<ReviewDTO>> violations = 
            validator.validate(dto, ReviewDTO.Create.class);
        
        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString())
            .isEqualTo("courseId");
    }
    
    @Test
    @DisplayName("Should validate Create group - valid DTO")
    void shouldValidateCreateGroupValidDTO() {
        // Given
        ReviewDTO dto = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment("Valid create request")
            .build();
        
        // When
        Set<ConstraintViolation<ReviewDTO>> violations = 
            validator.validate(dto, ReviewDTO.Create.class);
        
        // Then
        assertThat(violations).isEmpty();
    }
    
    @Test
    @DisplayName("Should validate Update group - courseId not required")
    void shouldValidateUpdateGroupCourseIdNotRequired() {
        // Given
        ReviewDTO dto = ReviewDTO.builder()
            .rating(4)
            .comment("Update without courseId")
            .build();
        
        // When
        Set<ConstraintViolation<ReviewDTO>> violations = 
            validator.validate(dto, ReviewDTO.Update.class);
        
        // Then
        assertThat(violations).isEmpty();
    }
    
    @Test
    @DisplayName("Should validate rating constraints")
    void shouldValidateRatingConstraints() {
        // Valid ratings (1-5)
        for (int rating = 1; rating <= 5; rating++) {
            ReviewDTO dto = ReviewDTO.builder()
                .courseId(1L)
                .rating(rating)
                .build();
            
            Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(dto);
            assertThat(violations).isEmpty();
        }
        
        // Invalid rating below 1
        ReviewDTO dtoBelowMin = ReviewDTO.builder()
            .courseId(1L)
            .rating(0)
            .build();
        
        Set<ConstraintViolation<ReviewDTO>> violationsBelowMin = validator.validate(dtoBelowMin);
        assertThat(violationsBelowMin).hasSize(1);
        assertThat(violationsBelowMin.iterator().next().getMessage())
            .contains("must be greater than or equal to 1");
        
        // Invalid rating above 5
        ReviewDTO dtoAboveMax = ReviewDTO.builder()
            .courseId(1L)
            .rating(6)
            .build();
        
        Set<ConstraintViolation<ReviewDTO>> violationsAboveMax = validator.validate(dtoAboveMax);
        assertThat(violationsAboveMax).hasSize(1);
        assertThat(violationsAboveMax.iterator().next().getMessage())
            .contains("must be less than or equal to 5");
    }
    
    @Test
    @DisplayName("Should validate comment size constraint")
    void shouldValidateCommentSizeConstraint() {
        // Valid comment at maximum length
        String maxLengthComment = "a".repeat(1000);
        ReviewDTO validDto = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment(maxLengthComment)
            .build();
        
        Set<ConstraintViolation<ReviewDTO>> validViolations = validator.validate(validDto);
        assertThat(validViolations).isEmpty();
        
        // Invalid comment exceeding maximum length
        String tooLongComment = "a".repeat(1001);
        ReviewDTO invalidDto = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment(tooLongComment)
            .build();
        
        Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(invalidDto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .contains("size must be between 0 and 1000");
    }
    
    @Test
    @DisplayName("Should handle null comment")
    void shouldHandleNullComment() {
        // Given
        ReviewDTO dto = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment(null)
            .build();
        
        // When
        Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(dto);
        
        // Then
        assertThat(violations).isEmpty();
        assertThat(dto.getComment()).isNull();
    }
    
    @Test
    @DisplayName("Should handle empty comment")
    void shouldHandleEmptyComment() {
        // Given
        ReviewDTO dto = ReviewDTO.builder()
            .courseId(1L)
            .rating(5)
            .comment("")
            .build();
        
        // When
        Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(dto);
        
        // Then
        assertThat(violations).isEmpty();
        assertThat(dto.getComment()).isEmpty();
    }
    
    @Test
    @DisplayName("Should validate null rating")
    void shouldValidateNullRating() {
        // Given
        ReviewDTO dto = ReviewDTO.builder()
            .courseId(1L)
            .rating(null)
            .comment("Rating is null")
            .build();
        
        // When
        Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(dto);
        
        // Then
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
            .contains("must not be null");
    }
    
    @Test
    @DisplayName("Should support builder pattern")
    void shouldSupportBuilderPattern() {
        // Given & When
        LocalDateTime now = LocalDateTime.now();
        ReviewDTO dto = ReviewDTO.builder()
            .id(1L)
            .studentId(2L)
            .courseId(3L)
            .rating(4)
            .comment("Test comment")
            .createdAt(now)
            .updatedAt(now)
            .build();
        
        // Then
        assertThat(dto)
            .extracting("id", "studentId", "courseId", "rating", "comment", "createdAt", "updatedAt")
            .containsExactly(1L, 2L, 3L, 4, "Test comment", now, now);
    }
    
    @Test
    @DisplayName("Should support equals and hashCode")
    void shouldSupportEqualsAndHashCode() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        
        ReviewDTO dto1 = ReviewDTO.builder()
            .id(1L)
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Test")
            .createdAt(now)
            .updatedAt(now)
            .build();
        
        ReviewDTO dto2 = ReviewDTO.builder()
            .id(1L)
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Test")
            .createdAt(now)
            .updatedAt(now)
            .build();
        
        ReviewDTO dto3 = ReviewDTO.builder()
            .id(2L) // Different ID
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Test")
            .createdAt(now)
            .updatedAt(now)
            .build();
        
        // Then
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).isNotEqualTo(dto3);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }
    
    @Test
    @DisplayName("Should support toString method")
    void shouldSupportToString() {
        // Given
        ReviewDTO dto = ReviewDTO.builder()
            .id(1L)
            .studentId(1L)
            .courseId(1L)
            .rating(5)
            .comment("Test comment")
            .build();
        
        // When
        String toString = dto.toString();
        
        // Then
        assertThat(toString)
            .contains("ReviewDTO")
            .contains("id=1")
            .contains("studentId=1")
            .contains("courseId=1")
            .contains("rating=5")
            .contains("comment=Test comment");
    }
    
    @Test
    @DisplayName("Should handle default constructor")
    void shouldHandleDefaultConstructor() {
        // Given & When
        ReviewDTO dto = new ReviewDTO();
        dto.setId(1L);
        dto.setRating(5);
        dto.setComment("Set via setter");
        
        // Then
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getRating()).isEqualTo(5);
        assertThat(dto.getComment()).isEqualTo("Set via setter");
    }
    
    @Test
    @DisplayName("Should handle all args constructor")
    void shouldHandleAllArgsConstructor() {
        // Given & When
        LocalDateTime now = LocalDateTime.now();
        ReviewDTO dto = new ReviewDTO(1L, 2L, now, now, 3L, 4, "Constructor test");
        
        // Then
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getStudentId()).isEqualTo(2L);
        assertThat(dto.getCourseId()).isEqualTo(3L);
        assertThat(dto.getRating()).isEqualTo(4);
        assertThat(dto.getComment()).isEqualTo("Constructor test");
        assertThat(dto.getCreatedAt()).isEqualTo(now);
        assertThat(dto.getUpdatedAt()).isEqualTo(now);
    }
}