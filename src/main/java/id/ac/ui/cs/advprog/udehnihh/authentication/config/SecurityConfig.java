package id.ac.ui.cs.advprog.udehnihh.authentication.config;

import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private static final String COURSE_ID = "/api/courses/{courseId}";

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/login", "/register", "/").permitAll() 
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/api/cb/courses").permitAll()
                .requestMatchers("/api/cb/courses/{id}").permitAll()
                .requestMatchers("/api/cb/courses/{id}/enroll").hasRole(Role.STUDENT.name())
                .requestMatchers("/api/cb/my-courses").hasRole(Role.STUDENT.name())
                .requestMatchers("/api/tutor-applications/**").authenticated()
                .requestMatchers("/courses", "/courses/**").permitAll()
                .requestMatchers("/my-courses").permitAll()
                .requestMatchers("/student/**").permitAll()

                // Course and Creation Management Endpoints
                .requestMatchers("/api/courses/**").permitAll()
                .requestMatchers("/api/courses/**").authenticated()
                .requestMatchers("/api/courses/lists").hasRole(Role.TUTOR.name())
                .requestMatchers(HttpMethod.POST, "/api/courses/create").hasRole(Role.TUTOR.name())
                .requestMatchers(HttpMethod.GET, COURSE_ID).hasRole(Role.TUTOR.name())
                .requestMatchers(HttpMethod.PUT, COURSE_ID).hasRole(Role.TUTOR.name())
                .requestMatchers(HttpMethod.DELETE, COURSE_ID).hasRole(Role.TUTOR.name())
                .requestMatchers("/api/courses/tutor/create").hasRole(Role.TUTOR.name())

                // Tutor Application Endpoints
                .requestMatchers("/api/tutor-applications/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/tutor-applications/status").hasRole(Role.STUDENT.name())
                .requestMatchers(HttpMethod.POST, "/api/tutor-applications/apply").hasRole(Role.STUDENT.name())
                .requestMatchers(HttpMethod.DELETE, "/api/tutor-applications/delete").hasRole(Role.STUDENT.name())

                // Report Endpoints
                .requestMatchers("/student/reports/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/student/reports/**").hasRole(Role.STUDENT.name())
                .requestMatchers(HttpMethod.POST, "/api/student/reports").hasRole(Role.STUDENT.name())
                .requestMatchers(HttpMethod.PUT, "/api/student/reports/{id}").hasRole(Role.STUDENT.name())
                .requestMatchers(HttpMethod.DELETE, "/api/student/reports/{id}").hasRole(Role.STUDENT.name())
                .requestMatchers("/api/staff/reports/**").hasRole(Role.STAFF.name())
                .anyRequest().denyAll()
            )
            .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
