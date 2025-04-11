package id.ac.ui.cs.advprog.udehnihh.authentication.model;
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
    private String role;
    private String gender;

    @CreationTimestamp
    private LocalDateTime registrationDate;

    private String phoneNumber;
    private String birthDate;
}
