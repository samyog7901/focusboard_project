package org.focusboard.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        // ✅ Skip JWT validation for public endpoints
        if (requestPath.startsWith("/api/auth/") ||
                requestPath.equals("/") ||
                requestPath.equals("/login") ||
                requestPath.equals("/register") ||
                requestPath.equals("/dashboard") ||  // Optional: dashboard may be protected
                requestPath.equals("/forgot-password") ||
                requestPath.equals("/verify-otp") ||
                requestPath.equals("/reset-password") ||
                requestPath.endsWith(".css") ||       // Optional: static assets
                requestPath.endsWith(".js") ||
                requestPath.endsWith(".png") ||
                requestPath.endsWith(".jpg") ||
                requestPath.startsWith("/static/")) {

            filterChain.doFilter(request, response);
            return;
        }

        // 🔐 Validate JWT
        final String authHeader = request.getHeader("Authorization");
        String email = null;
        String role = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);

            try {
                if (jwtUtil.validateToken(jwt)) {
                    Claims claims = jwtUtil.getClaims(jwt);
                    email = claims.getSubject();
                    role = claims.get("role", String.class);

                    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        var auth = new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            } catch (JwtException e) {
                // Optional: you can log or send an error response
            }
        }

        filterChain.doFilter(request, response);
    }
}