package com.roaster.config;

import io.github.bucket4j.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements Filter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket getBucket(String ip) {
        return buckets.computeIfAbsent(ip, k ->
            Bucket4j.builder()
                .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1))))
                .build()
        );
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String ip = request.getRemoteAddr();
        Bucket bucket = getBucket(ip);

        if (bucket.tryConsume(1)) {
            chain.doFilter(req, res);
        } else {
            response.setStatus(429);
            response.getWriter().write("Too many roasts. Try again later 😈");
        }
    }
}
