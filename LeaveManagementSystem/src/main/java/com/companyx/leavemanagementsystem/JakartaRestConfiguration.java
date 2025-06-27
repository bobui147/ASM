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
        // Đăng ký JacksonFeature để hỗ trợ JSON
        classes.add(org.glassfish.jersey.jackson.JacksonFeature.class);
        // Thêm các REST resources của bạn
        classes.add(com.companyx.leavemanagementsystem.rest.UserResource.class); // Ví dụ
        classes.add(com.companyx.leavemanagementsystem.rest.LeaveRequestResource.class); // Ví dụ
        return classes;
    }
}