package com.ap.iamstu.application.config;

import com.ap.iamstu.infrastructure.support.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Configuration
@WebFilter("/api/**")
@Slf4j
public class StatsTracingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        Instant start = Instant.now();
        try {
            chain.doFilter(req, resp);
        } finally {
            Instant finish = Instant.now();
            long time = Duration.between(start, finish).toMillis();
            Optional<String> optionalCurrentUserId = SecurityUtils.getCurrentUser();
            if (optionalCurrentUserId.isEmpty()) {
                log.info("Execute api: {}, status: {}, time: {} ms ", ((HttpServletRequest) req).getRequestURI(),
                        ((HttpServletResponse) resp).getStatus(),
                        time);
            } else {
                log.info("User/client: {}, execute api: {}, status: {}, time: {} ms ", optionalCurrentUserId.get(),
                        ((HttpServletRequest) req).getRequestURI(),
                        ((HttpServletResponse) resp).getStatus(),
                        time);
            }
        }
    }
}
