package id.ac.ui.cs.advprog.udehnihh.report.repository;

import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;


    @Test
    @DisplayName("Should find all reports by author")
    void testFindAllByAuthor() {
        Report r1 = new Report("system", "alice", "Title 1", "Desc 1");
        Report r2 = new Report("system", "bob", "Title 2", "Desc 2");
        Report r3 = new Report("system", "alice", "Title 3", "Desc 3");

        reportRepository.saveAll(List.of(r1, r2, r3));

        List<Report> results = reportRepository.findAllByAuthor("alice");

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(r -> r.getAuthor().equals("alice")));
    }

}
