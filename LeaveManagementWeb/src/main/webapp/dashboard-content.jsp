<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.leave.leavemanagementweb.model.User" %>
<%
    User user = (User) session.getAttribute("user");
%>
<div class="container-fluid pt-4 px-4">
    <div class="bg-secondary text-light rounded p-4">
        <h2 class="mb-4">Welcome, <%= user.getFullName() %>!</h2>
        <p><strong>Username:</strong> <%= user.getUsername() %></p>
        <p><strong>User ID:</strong> <%= user.getUserId() %></p>
    </div>
</div>
