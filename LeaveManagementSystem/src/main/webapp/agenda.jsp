<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Agenda</title></head>
<body>
    <h2>Leave Agenda</h2>
    <form action="agenda" method="get">
        <label>Start Date: <input type="date" name="startDate"></label>
        <label>End Date: <input type="date" name="endDate"></label>
        <input type="submit" value="Generate">
    </form>
    <c:if test="${not empty dates}">
        <table border="1">
            <tr><th>Employee</th><c:forEach var="date" items="${dates}"><th>${date}</th></c:forEach></tr>
            <c:forEach var="entry" items="${leaveMap}">
                <tr>
                    <td>${entry.key}</td>
                    <c:forEach var="date" items="${dates}">
                        <td>${leaveMap[entry.key][date] ? 'Off' : 'On'}</td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <p style="color:red">${error}</p>
    <a href="dashboard.jsp">Back to Dashboard</a>
</body>
</html>