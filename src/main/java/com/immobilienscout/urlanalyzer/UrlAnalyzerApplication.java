package com.immobilienscout.urlanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class UrlAnalyzerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UrlAnalyzerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(UrlAnalyzerApplication.class, args);
    }
}
