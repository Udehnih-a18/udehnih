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
                .requestMatchers("/auth/**").permitAll()  // Login/register pages
                .requestMatchers("/login", "/register", "/").permitAll()  // Public pages
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()  // Static resources
                .requestMatchers("/api/cb/courses").permitAll()  // Allow browsing courses
                .requestMatchers("/api/cb/courses/{id}").permitAll()  // Allow viewing course details
                .requestMatchers("/api/cb/courses/{id}/enroll").hasRole("STUDENT")
                .requestMatchers("/api/cb/my-courses").hasRole("STUDENT")
                .requestMatchers("/api/tutor-applications/**").authenticated()
                .requestMatchers("/courses", "/courses/**").permitAll()  // Web pages for courses
                .requestMatchers("/my-courses").permitAll()  // Web page (auth checked by frontend)
                    .requestMatchers(HttpMethod.GET, "/api/student/reports/**").hasRole("STUDENT")
                    .requestMatchers(HttpMethod.POST, "/api/student/reports").hasRole("STUDENT")
                    .requestMatchers(HttpMethod.PUT, "/api/student/reports/{id}").hasRole("STUDENT")
                    .requestMatchers(HttpMethod.DELETE, "/api/student/reports/{id}").hasRole("STUDENT")
                    .requestMatchers("/api/staff/reports/**").hasRole("STAFF")
                .anyRequest().permitAll()  // Change to permitAll for debugging
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
