package id.ac.ui.cs.advprog.udehnihh.repository;

import id.ac.ui.cs.advprog.udehnihh.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CbSectionRepository
        extends JpaRepository<Course, UUID>, JpaSpecificationExecutor<Course> {}
