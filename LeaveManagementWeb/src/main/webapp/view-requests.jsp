<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.leave.leavemanagementweb.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    User user = (User) session.getAttribute("user");
    boolean canProcess = user != null && (
        "manager".equalsIgnoreCase(user.getRole()) ||
        "supermanager".equalsIgnoreCase(user.getRole())
    );
    request.setAttribute("canProcess", canProcess);
%>

<div class="container-fluid pt-4 px-4">
    <div class="bg-secondary text-light rounded p-4">
        <h4 class="mb-4">Leave Requests</h4>

        <c:if test="${empty requests}">
            <p class="text-warning">No leave requests found.</p>
        </c:if>

        <c:if test="${not empty requests}">
            <table class="table table-dark table-striped table-bordered align-middle">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Employee</th>
                        <th>Start</th>
                        <th>End</th>
                        <th>Reason</th>
                        <th>Status</th>
                        <th>Processed By</th>
                        <th>Note</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="req" items="${requests}" varStatus="i">
                        <tr>
                            <td>${i.index + 1}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty req.employeeName}">
                                        ${req.employeeName}
                                    </c:when>
                                    <c:otherwise>
                                        ${req.requesterName}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${req.startDate}</td>
                            <td>${req.endDate}</td>
                            <td>${req.reason}</td>
                            <td>${req.status}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty req.processedByName}">
                                        ${req.processedByName}
                                    </c:when>
                                    <c:otherwise>N/A</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:out value="${req.processedReason}" default="-" />
                            </td>
                            <td>
                                <c:if test="${canProcess and req.status == 'Inprogress'}">
                                    <form action="approval" method="get" style="display:inline;">
                                        <input type="hidden" name="id" value="${req.requestId}" />
                                        <button type="submit" class="btn btn-sm btn-warning">Process</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
