package com.example.Active.Razgrad.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/user-profile").setViewName("user-profile");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path userUploadDir = Paths.get("./user-photos");
        String userUploadPath = userUploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/user-photos/**").addResourceLocations("file:/"+ userUploadPath + "/");
        //exposeDirectory("user-photos", registry);
    }
// private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
//        Path uploadDir = Paths.get(dirName);
//        String uploadPath = uploadDir.toFile().getAbsolutePath();
//
//        if (dirName.startsWith("../")) dirName = dirName.replace("../", "");
//
//        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ uploadPath + "/");
//    }

}
