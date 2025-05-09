package id.ac.ui.cs.advprog.udehnihh.authentication.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private List<String> errors;

    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse(List<String> errors) {
        this.errors = errors;
    }
}
