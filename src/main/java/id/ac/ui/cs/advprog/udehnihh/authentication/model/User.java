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
public class User {

    private String email;
    private String fullName;
    private String password;
    @Builder.Default
    private Role role = Role.STUDENT;
    private Gender gender;

    @CreationTimestamp
    private LocalDateTime registrationDate;

    private String phoneNumber;
    private String birthDate;
}
