package com.companyx.leavemanagementsystem;

import com.companyx.leavemanagementsystem.model.LeaveRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("leaveRequests")
public class LeaveRequestResource {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("LeaveManagementPU");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<LeaveRequest> getAllLeaveRequests() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM LeaveRequest l", LeaveRequest.class).getResultList();
        } finally {
            em.close();
        }
    }
}