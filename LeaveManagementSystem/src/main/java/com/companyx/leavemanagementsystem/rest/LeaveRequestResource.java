package com.companyx.leavemanagementsystem.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/leave-requests")
public class LeaveRequestResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getLeaveRequests() {
        return "List of leave requests";
    }
}