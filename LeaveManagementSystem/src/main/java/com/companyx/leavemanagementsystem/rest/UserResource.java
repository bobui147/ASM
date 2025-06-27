package com.companyx.leavemanagementsystem.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
public class UserResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUsers() {
        return "List of users";
    }
}