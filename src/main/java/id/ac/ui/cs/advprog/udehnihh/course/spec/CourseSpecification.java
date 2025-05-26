package id.ac.ui.cs.advprog.udehnihh.course.spec;

import id.ac.ui.cs.advprog.udehnihh.course.model.Course;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {
    public static Specification<Course> nameContains(String q) {
        return (root, query, cb) ->
                q == null ? null : cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%");
    }

    public static Specification<Course> priceBetween(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("price"), min, max);
            return min != null ? cb.greaterThanOrEqualTo(root.get("price"), min)
                               : cb.lessThanOrEqualTo(root.get("price"), max);
        };
    }
}
