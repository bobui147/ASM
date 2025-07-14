<%@ page import="com.leave.leavemanagementweb.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    boolean canProcess = user != null && (
        "manager".equalsIgnoreCase(user.getRole()) || "supermanager".equalsIgnoreCase(user.getRole())
    );
%>

<div class="sidebar pe-4 pb-3 bg-dark">
    <nav class="navbar bg-dark navbar-dark">
        <a href="dashboard.jsp" class="navbar-brand mx-4 mb-3">
            <h4 class="text-primary text-wrap text-center" style="font-size: 22px; white-space: normal;">
                <i class="fas fa-user-edit me-2"></i>Leave Management
            </h4>
        </a>
        <div class="navbar-nav w-100">
            <a href="dashboard.jsp" class="nav-item nav-link">
                <i class="fa fa-tachometer-alt me-2"></i>Dashboard
            </a>
            <a href="leave-request" class="nav-item nav-link">
                <i class="fa fa-calendar-plus me-2"></i>Request Leave
            </a>
            <a href="view-requests" class="nav-item nav-link">
                <i class="fa fa-list-alt me-2"></i>View Requests
            </a>
            <% if (canProcess) { %>
            <a href="view-requests" class="nav-item nav-link">
                <i class="fa fa-check-circle me-2"></i>Process Requests
            </a>
            <% } %>
        </div>
    </nav>
</div>
