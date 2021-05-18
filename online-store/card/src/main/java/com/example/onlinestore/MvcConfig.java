package com.example.onlinestore;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/add").setViewName("add");
    registry.addViewController("/").setViewName("cards");
    registry.addViewController("/reward").setViewName("reward");
    registry.addViewController("/cards").setViewName("cards");
    registry.addViewController("/login").setViewName("login");
   

  }

}