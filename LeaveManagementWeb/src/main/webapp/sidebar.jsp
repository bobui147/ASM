<%@ page import="com.leave.leavemanagementweb.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    boolean canProcess = user != null && (
        "manager".equalsIgnoreCase(user.getRole()) || 
        "supermanager".equalsIgnoreCase(user.getRole())
    );
    boolean canProcess2 = user != null && "supermanager".equalsIgnoreCase(user.getRole());
%>

<div style="color:yellow">ROLE: <%= user != null ? user.getRole() : "null" %></div>

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
                <i class="fa fa-list-alt me-2"></i>View My Requests
            </a>
            <% if (canProcess) { %>
            <a href="leave-list" class="nav-item nav-link">
                <i class="fa fa-tasks me-2"></i>Approve/Reject
            </a>
            <% } %>
            <% if (canProcess2) { %>
            <a href="agenda" class="nav-item nav-link">
                <i class="fa fa-table me-2"></i>Agenda
            </a>
            <% } %>
        </div>
    </nav>
</div>
