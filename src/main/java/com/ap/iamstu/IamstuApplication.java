package com.ap.iamstu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties
public class IamstuApplication {

    public static void main(String[] args) {
        SpringApplication.run(IamstuApplication.class, args);
    }

}
