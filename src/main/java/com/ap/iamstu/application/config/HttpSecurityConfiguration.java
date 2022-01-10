package com.ap.iamstu.application.config;

import com.ap.iamstu.application.sercurity.CustomAuthenticationFilter;
import com.ap.iamstu.application.sercurity.ForbiddenTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@Slf4j
@EnableWebSecurity
@Import(SecurityProblemSupport.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class HttpSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final SecurityProblemSupport problemSupport;
    private final CustomAuthenticationFilter customAuthenticationFilter;
    private final ForbiddenTokenFilter forbiddenTokenFilter;

    public HttpSecurityConfiguration(CorsFilter corsFilter, SecurityProblemSupport problemSupport, CustomAuthenticationFilter customAuthenticationFilter, ForbiddenTokenFilter forbiddenTokenFilter) {
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
        this.customAuthenticationFilter = customAuthenticationFilter;
        this.forbiddenTokenFilter = forbiddenTokenFilter;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers("/js/*.{js,html}")
                .antMatchers("/i18n/**")
                .antMatchers("/content/**")
                .antMatchers(HttpMethod.GET, "/api/public/**")
                .antMatchers(HttpMethod.POST, "/api/public/**")
                .antMatchers("/swagger-ui/index.html");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuditorAware<String> springSecurityAuditorAware() {
        return new SpringSecurityAuditorAware();
    }

    //    @Bean
//    public JwtRequestParamFilter jwtRequestParamFilter() {
//        return new JwtRequestParamFilter();
//    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf()
                .disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/files/download-ios-app").permitAll()
                .antMatchers("/api/certificate/.well-known/jwks.json").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/refresh-token").permitAll()
                .antMatchers("/api/authenticate/**").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/activate").permitAll()
                .antMatchers("/api/account/reset-password/init").permitAll()
                .antMatchers("/api/account/reset-password/confirm").permitAll()
                .antMatchers("/api/account/reset-password/finish").permitAll()
                .antMatchers("/api/client/authenticate").permitAll()
                .antMatchers("/swagger-*/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/api/**").authenticated();
//                .and()
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//    http.addFilterBefore(jwtRequestParamFilter(), BearerTokenAuthenticationFilter.class);
        http.addFilterAfter(forbiddenTokenFilter, BearerTokenAuthenticationFilter.class);
        http.addFilterAfter(customAuthenticationFilter, BearerTokenAuthenticationFilter.class);

        // @formatter:on
    }
}
