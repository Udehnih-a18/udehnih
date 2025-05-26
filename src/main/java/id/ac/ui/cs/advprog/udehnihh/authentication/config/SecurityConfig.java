package id.ac.ui.cs.advprog.udehnihh.authentication.config;

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
                .requestMatchers("/api/cb/courses/{id}/enroll").hasRole("STUDENT")
                .requestMatchers("/api/cb/my-courses").hasRole("STUDENT")
                .requestMatchers("/api/tutor-applications/**").authenticated()
                .requestMatchers("/courses", "/courses/**").permitAll()
                .requestMatchers("/my-courses").permitAll()
                .requestMatchers("/student/**").permitAll()

                // Course and Creation Management Endpoints
                .requestMatchers("/api/courses/**").permitAll()
                .requestMatchers("/api/courses/**").authenticated()
                .requestMatchers("/api/courses/lists").hasRole("TUTOR")
                .requestMatchers(HttpMethod.POST, "/api/courses/create").hasRole("TUTOR")
                .requestMatchers(HttpMethod.GET, "/api/courses/{courseId}").hasRole("TUTOR")
                .requestMatchers(HttpMethod.PUT, "/api/courses/{courseId}").hasRole("TUTOR")
                .requestMatchers(HttpMethod.DELETE, "/api/courses/{courseId}").hasRole("TUTOR")
                .requestMatchers("/api/courses/tutor/create").hasRole("TUTOR")

                // Tutor Application Endpoints
                .requestMatchers("/api/tutor-applications/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/tutor-applications/status").hasRole("STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/tutor-applications/apply").hasRole("STUDENT")
                .requestMatchers(HttpMethod.DELETE, "/api/tutor-applications/delete").hasRole("STUDENT")

                // Report Endpoints
                .requestMatchers("/student/reports/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/student/reports/**").hasRole("STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/student/reports").hasRole("STUDENT")
                .requestMatchers(HttpMethod.PUT, "/api/student/reports/{id}").hasRole("STUDENT")
                .requestMatchers(HttpMethod.DELETE, "/api/student/reports/{id}").hasRole("STUDENT")
                .requestMatchers("/api/staff/reports/**").hasRole("STAFF")
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
