<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.leave.leavemanagementweb.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Leave Management</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/style.css" rel="stylesheet" />
    <link href="lib/fontawesome/css/all.min.css" rel="stylesheet" />
</head>
<body class="bg-dark text-light">
<div class="container-fluid d-flex p-0">
    <jsp:include page="sidebar.jsp"/>
    <div class="content w-100">
        <jsp:include page="topbar.jsp"/>
        <jsp:include page="${pageContent}" />
    </div>
</div>
</body>
</html>
