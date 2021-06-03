package fei.stuba.bp.rigo.preteky.config;


import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry){
        Path logoUploadDir = Paths.get("logos");
        String logoUploadPath = logoUploadDir.toFile().getAbsolutePath();
        resourceHandlerRegistry.addResourceHandler("/logos/**").addResourceLocations("file:/"+logoUploadPath+"/");
    }
}
