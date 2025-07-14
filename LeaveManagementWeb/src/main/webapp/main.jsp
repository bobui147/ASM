<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.leave.leavemanagementweb.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="icon" href="img/favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/style.css" rel="stylesheet" />
    <link href="lib/fontawesome/css/all.min.css" rel="stylesheet" />
</head>
<body class="bg-dark text-light">
<div class="container-fluid d-flex p-0">
    <jsp:include page="sidebar.jsp"/>
    <div class="content w-100">
        <jsp:include page="topbar.jsp"/>

        <c:choose>
            <c:when test="${not empty pageContent}">
                <jsp:include page="${pageContent}" />
            </c:when>
            <c:otherwise>
                <p class="text-center mt-5">No content to display.</p>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<!-- JS -->
<script src="lib/jquery/jquery.min.js"></script>
<script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="js/main.js"></script>
</body>
</html>
