<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Create Leave</title></head>
<body>
    <h2>Create Leave Request</h2>
    <form action="createLeave" method="post">
        <label>Title: <input type="text" name="title"></label><br>
        <label>From Date: <input type="date" name="fromDate"></label><br>
        <label>To Date: <input type="date" name="toDate"></label><br>
        <label>Reason: <input type="text" name="reason"></label><br>
        <input type="submit" value="Submit">
    </form>
    <p style="color:red">${error}</p>
</body>
</html>