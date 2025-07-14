<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.leave.leavemanagementweb.model.LeaveRequest" %>
<%
    LeaveRequest req = (LeaveRequest) request.getAttribute("request");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Approve/Reject Leave Request</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body class="bg-dark text-light">
<div class="container mt-5">
    <h2>Leave Request Review</h2>
    <form action="approval" method="post">
        <input type="hidden" name="requestId" value="<%= req.getRequestId() %>">
        
        <div class="mb-3">
            <label>Employee:</label>
            <input class="form-control" readonly value="<%= req.getEmployeeName() %>">
        </div>
        <div class="mb-3">
            <label>Start Date:</label>
            <input class="form-control" readonly value="<%= req.getStartDate() %>">
        </div>
        <div class="mb-3">
            <label>End Date:</label>
            <input class="form-control" readonly value="<%= req.getEndDate() %>">
        </div>
        <div class="mb-3">
            <label>Reason:</label>
            <textarea class="form-control" readonly><%= req.getReason() %></textarea>
        </div>
        <div class="mb-3">
            <label>Approval Decision:</label><br>
            <input type="radio" name="action" value="Approved" required> Approve
            <input type="radio" name="action" value="Rejected" class="ms-3"> Reject
        </div>
        <div class="mb-3">
            <label>Processing Note:</label>
            <textarea name="note" class="form-control"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit Decision</button>
        <a href="view-requests" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>
