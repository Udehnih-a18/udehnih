package id.ac.ui.cs.advprog.udehnihh.course.repository;

import id.ac.ui.cs.advprog.udehnihh.course.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CbSectionRepository
        extends JpaRepository<Section, UUID>, JpaSpecificationExecutor<Section> {}
