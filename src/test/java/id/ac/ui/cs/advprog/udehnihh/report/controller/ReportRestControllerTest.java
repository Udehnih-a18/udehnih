package id.ac.ui.cs.advprog.udehnihh.report.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.AuthService;
import id.ac.ui.cs.advprog.udehnihh.report.model.Report;
import id.ac.ui.cs.advprog.udehnihh.report.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReportRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReportService reportService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;
    private User testUser;
    private Report testReport;

    @BeforeEach
    void setUp() {
        reset(reportService);
        reset(authService);
        baseUrl = "http://localhost:" + port + "/api";

        testUser = User.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .fullName("Test User")
                .build();

        testReport = Report.builder()
                .idReport("r123")
                .title("Sample Title")
                .description("Sample Description")
                .createdBy(testUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldCreateReportSuccessfully() {
        when(authService.getCurrentUser()).thenReturn(testUser);
        when(reportService.createReport(any(Report.class))).thenReturn(testReport);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Report> request = new HttpEntity<>(testReport, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/student/reports", request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(reportService).createReport(any(Report.class));
    }

    @Test
    void shouldReturnReportsForStudent() {
        when(authService.getCurrentUser()).thenReturn(testUser);
        when(reportService.getReportsByAuthor(testUser)).thenReturn(List.of(testReport));

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/student/reports", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(reportService).getReportsByAuthor(testUser);
    }

    @Test
    void shouldDenyAccessToOtherUserReport() {
        User otherUser = User.builder().id(UUID.randomUUID()).email("x@x.com").build();
        when(authService.getCurrentUser()).thenReturn(otherUser);
        when(reportService.getReportById("r123")).thenReturn(testReport);

        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/student/reports/r123", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldReturnReportByIdForStaff() {
        when(reportService.getReportById("r123")).thenReturn(testReport);
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/staff/reports/r123", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldUpdateOwnReport() {
        Report updated = Report.builder().title("New").description("Updated").build();
        when(authService.getCurrentUser()).thenReturn(testUser);
        when(reportService.updateReport(eq("r123"), eq(testUser), any(), any())).thenReturn(testReport);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Report> request = new HttpEntity<>(updated, headers);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/student/reports/r123", HttpMethod.PUT, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldDeleteOwnReport() {
        when(authService.getCurrentUser()).thenReturn(testUser);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/student/reports/r123", HttpMethod.DELETE, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnEmptyListWhenNoReportsExist() {
        when(reportService.getAllReports()).thenReturn(List.of());
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/staff/reports", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public ReportService reportService() {
            return mock(ReportService.class);
        }

        @Bean
        @Primary
        public AuthService authService() {
            return mock(AuthService.class);
        }
    }
}