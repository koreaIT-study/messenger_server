package com.teamride.messenger.server.config;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    HttpSession httpSession;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/templates/", "classpath:/static/")
            .setCachePeriod(0);
        // .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));

        registry.addResourceHandler("/upload/**")
        .addResourceLocations("file:/profile/");

        registry.addResourceHandler("/msgFile/**")
                .addResourceLocations("file:/msg/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://35.216.1.250:12001");
    }
}
