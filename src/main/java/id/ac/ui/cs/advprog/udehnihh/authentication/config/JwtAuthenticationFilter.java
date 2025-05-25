package id.ac.ui.cs.advprog.udehnihh.authentication.config;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User; 
import id.ac.ui.cs.advprog.udehnihh.authentication.service.JwtServiceImpl;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.TokenBlacklistService;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository; 

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;
    private final UserRepository userRepository; 

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token.");
            return;
        }

        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token has been logged out.");
            return;
        }

        String email = jwtUtil.getEmailFromToken(token);
        String role = jwtUtil.getRoleFromToken(token);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails = userRepository.findByEmail(email).orElse(null); 

            if (userDetails != null) { 

                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } 
        } 
        filterChain.doFilter(request, response);
    }
}
