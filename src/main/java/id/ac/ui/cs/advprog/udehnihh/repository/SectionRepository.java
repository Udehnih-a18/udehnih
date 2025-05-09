package id.ac.ui.cs.advprog.udehnihh.repository;

import id.ac.ui.cs.advprog.udehnihh.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SectionRepository extends JpaRepository<Section, UUID> {
    List<Section> findByCourseId(UUID courseId);
}
