package id.ac.ui.cs.advprog.udehnihh.authentication.model;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.STUDENT;

    @CreationTimestamp
    private LocalDateTime registrationDate;

    @PrePersist
    protected void onCreate() {
        this.registrationDate = LocalDateTime.now();
    }
}
