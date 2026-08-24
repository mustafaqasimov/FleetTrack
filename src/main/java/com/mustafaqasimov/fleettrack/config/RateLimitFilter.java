package com.mustafaqasimov.fleettrack.config;

import com.mustafaqasimov.fleettrack.service.RateLimitingService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitingService rateLimitingService;

    public RateLimitFilter(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/auth/login")) {

            String ipAddress = request.getRemoteAddr();

            Bucket bucket = rateLimitingService.resolveBucket(ipAddress);

            if (bucket.tryConsume(1)) {

                filterChain.doFilter(request, response);

            } else {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value()); // 429 xətası
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\": \"Çoxlu uğursuz giriş cəhdi. 1 dəqiqə gözləyin.\"}");
                return;
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }
}
