package com.companyx.leavemanagementsystem;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        // Tạm thời comment để tránh lỗi nếu Jackson chưa tải
         classes.add(org.glassfish.jersey.jackson.JacksonFeature.class);
        return classes;
    }
}