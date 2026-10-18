package com.example.spring_zavrsni_zadatak.configuration;

import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(
                "/evidencija",
                controller -> controller.getPackageName()
                        .equals("com.example.spring_zavrsni_zadatak.controller")
        );
    }
}
