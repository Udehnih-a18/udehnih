package id.ac.ui.cs.advprog.udehnihh.strategy;
import id.ac.ui.cs.advprog.udehnihh.model.Course;
import java.util.List;

public interface CourseSearchStrategy {
    List<Course> search(String query);
}

