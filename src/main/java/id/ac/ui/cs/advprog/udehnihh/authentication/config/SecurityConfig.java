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
import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;



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
                .requestMatchers("/**").permitAll()
                .requestMatchers("/api/cb/courses").permitAll()
                .requestMatchers("/api/cb/courses/{id}").permitAll()
                .requestMatchers("/api/cb/courses/{id}/enroll").hasRole(Role.STUDENT.name())
                .requestMatchers("/api/cb/my-courses").hasRole(Role.STUDENT.name())
                    .requestMatchers(HttpMethod.GET, "/api/student/reports/**").hasRole(Role.STUDENT.name())
                    .requestMatchers(HttpMethod.POST, "/api/student/reports").hasRole(Role.STUDENT.name())
                    .requestMatchers(HttpMethod.PUT, "/api/student/reports/{id}").hasRole(Role.STUDENT.name())
                    .requestMatchers(HttpMethod.DELETE, "/api/student/reports/{id}").hasRole(Role.STUDENT.name())
                    .requestMatchers("/api/staff/reports/**").hasRole(Role.STAFF.name())

                    .anyRequest().authenticated()
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
