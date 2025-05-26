package id.ac.ui.cs.advprog.udehnihh.course.spec;

import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseSpecificationTest {

    @Test
    void testNameContainsNull() {
        Specification<Course> spec = CourseSpecification.nameContains(null);
        assertNull(spec.toPredicate(null, null, null));
    }

    @Test
    void testNameContainsWithValue() {
        Root<Course> root = mock(Root.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Predicate predicate = mock(Predicate.class);

        when(cb.lower(root.get("name"))).thenReturn(null);
        when(cb.like(any(), eq("%java%"))).thenReturn(predicate);

        Specification<Course> spec = CourseSpecification.nameContains("Java");
        Predicate result = spec.toPredicate(root, query, cb);

        assertEquals(predicate, result);
    }

    @Test
    void testPriceBetweenBothNull() {
        Specification<Course> spec = CourseSpecification.priceBetween(null, null);
        assertNull(spec.toPredicate(null, null, null));
    }

    @Test
    void testPriceBetweenBothNotNull() {
        Root<Course> root = mock(Root.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Predicate predicate = mock(Predicate.class);

        when(cb.between(root.get("price"), 100.0, 200.0)).thenReturn(predicate);

        Specification<Course> spec = CourseSpecification.priceBetween(100.0, 200.0);
        Predicate result = spec.toPredicate(root, query, cb);

        assertEquals(predicate, result);
    }

    @Test
    void testPriceBetweenOnlyMin() {
        Root<Course> root = mock(Root.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Predicate predicate = mock(Predicate.class);

        when(cb.greaterThanOrEqualTo(root.get("price"), 150.0)).thenReturn(predicate);

        Specification<Course> spec = CourseSpecification.priceBetween(150.0, null);
        Predicate result = spec.toPredicate(root, query, cb);

        assertEquals(predicate, result);
    }

    @Test
    void testPriceBetweenOnlyMax() {
        Root<Course> root = mock(Root.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Predicate predicate = mock(Predicate.class);

        when(cb.lessThanOrEqualTo(root.get("price"), 300.0)).thenReturn(predicate);

        Specification<Course> spec = CourseSpecification.priceBetween(null, 300.0);
        Predicate result = spec.toPredicate(root, query, cb);

        assertEquals(predicate, result);
    }
}