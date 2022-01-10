package com.ap.iamstu.application.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@WebFilter("/api/**")
@Slf4j
@Profile({"local", "dev", "prod"})
public class StatsTracingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        Instant start = Instant.now();
        try {
            chain.doFilter(req, resp);
        } finally {
            Instant finish = Instant.now();
            long time = Duration.between(start, finish).toMillis();
            log.info("Time execute {}: {} ms ", ((HttpServletRequest) req).getRequestURI(), time);
        }
    }
}
