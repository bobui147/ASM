<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>View Leaves</title></head>
<body>
    <h2>Leave Requests</h2>
    <table border="1">
        <tr><th>ID</th><th>Title</th><th>From</th><th>To</th><th>Status</th><th>Action</th></tr>
        <c:forEach var="leave" items="${leaveRequests}">
            <tr>
                <td>${leave.leaveID}</td>
                <td>${leave.title}</td>
                <td>${leave.fromDate}</td>
                <td>${leave.toDate}</td>
                <td>${leave.status}</td>
                <td><a href="approveLeave?leaveID=${leave.leaveID}">Approve/Reject</a></td>
            </tr>
        </c:forEach>
    </table>
    <p style="color:red">${error}</p>
    <a href="dashboard.jsp">Back to Dashboard</a>
</body>
</html>