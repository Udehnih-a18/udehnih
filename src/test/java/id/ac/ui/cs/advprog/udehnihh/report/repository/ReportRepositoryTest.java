package id.ac.ui.cs.advprog.udehnihh.report.repository;

import id.ac.ui.cs.advprog.udehnihh.UdehnihhApplication;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = UdehnihhApplication.class)
@DataJpaTest
public class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    private User createDummyUser(String email) {
        User user = User.builder()
                .email(email)
                .fullName("Dummy User")
                .password("secret123")
                .role(Role.STUDENT)
                .build();
        return userRepository.save(user);
    }

    @Test
    void testFindAllByCreatedBy() {
        User managedUser = createDummyUser("test@user.com");

        Report report1 = Report.builder()
                .title("First Report")
                .description("Description 1")
                .createdBy(managedUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Report report2 = Report.builder()
                .title("Second Report")
                .description("Description 2")
                .createdBy(managedUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        reportRepository.save(report1);
        reportRepository.save(report2);

        List<Report> reports = reportRepository.findAllByCreatedBy(managedUser);

        assertEquals(2, reports.size());
        assertTrue(reports.stream().allMatch(r -> r.getCreatedBy().getId().equals(managedUser.getId())));
    }

    @Test
    void testFindAllByCreatedBy_Empty() {
        User managedUser = createDummyUser("empty@user.com");
        List<Report> reports = reportRepository.findAllByCreatedBy(managedUser);
        assertTrue(reports.isEmpty());
    }
}