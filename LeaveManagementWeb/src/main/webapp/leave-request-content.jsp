<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.leave.leavemanagementweb.model.User" %>
<%
    User user = (User) session.getAttribute("user");
%>

<div class="container-fluid pt-4 px-4">
    <div class="bg-secondary text-light rounded p-4">
        <h2 class="mb-4">New Leave Request</h2>
        <div class="mb-4 bg-dark p-3 rounded">
            <p><strong class="text-white">Full Name:</strong> <%= user.getFullName() %></p>
            <p><strong class="text-white">Username:</strong> <%= user.getUsername() %></p>
            <p><strong class="text-white">Role:</strong> ${role}</p>
            <p><strong class="text-white">Department:</strong> ${department}</p>
        </div>
        <form action="leave-request" method="post">
            <div class="mb-3">
                <label for="startDate" class="form-label">Start Date</label>
                <input type="date" class="form-control" name="startDate" id="startDate" required>
            </div>
            <div class="mb-3">
                <label for="endDate" class="form-label">End Date</label>
                <input type="date" class="form-control" name="endDate" id="endDate" required>
            </div>
            <div class="mb-3">
                <label for="reason" class="form-label">Reason</label>
                <textarea class="form-control" name="reason" id="reason" rows="4" required></textarea>
            </div>
            <button type="submit" class="btn btn-danger w-100">Submit Request</button>
        </form>
        <p class="text-warning mt-2">${requestScope.error}</p>
    </div>
</div>
