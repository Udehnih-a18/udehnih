package id.ac.ui.cs.advprog.udehnihh.report.repository;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    List<Report> findAllByAuthor(String author);
}