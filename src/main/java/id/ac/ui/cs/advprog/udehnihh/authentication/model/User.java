package id.ac.ui.cs.advprog.udehnihh.authentication.model;

import enums.Gender;
import enums.Role;
import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String fullName;
    private String password;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @CreationTimestamp
    private LocalDateTime registrationDate;

    private String phoneNumber;
    private String birthDate;
}
