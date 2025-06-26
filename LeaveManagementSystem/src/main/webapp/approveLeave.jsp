<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Approve Leave</title></head>
<body>
    <h2>Approve/Reject Leave</h2>
    <form action="approveLeave" method="post">
        <input type="hidden" name="requestId" value="${leave.requestId}">
        <p>Title: ${leave.title}</p>
        <p>Start: ${leave.startDate}</p>
        <p>End: ${leave.endDate}</p>
        <p>Reason: ${leave.reason}</p>
        <label>Processed Reason: <input type="text" name="processedReason"></label><br>
        <input type="submit" name="action" value="approve"> 
        <input type="submit" name="action" value="reject">
    </form>
    <p style="color:red">${error}</p>
    <a href="viewLeaves">Back</a>
</body>
</html>