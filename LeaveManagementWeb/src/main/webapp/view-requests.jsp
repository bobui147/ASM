<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.leave.leavemanagementweb.model.LeaveRequest" %>
<%@ page import="com.leave.leavemanagementweb.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User user = (User) session.getAttribute("user");
    boolean canProcess = user != null && (
        "manager".equalsIgnoreCase(user.getRole()) || "supermanager".equalsIgnoreCase(user.getRole())
    );
    request.setAttribute("canProcess", canProcess);

    List<LeaveRequest> requests = (List<LeaveRequest>) request.getAttribute("requests");
%>

<div class="container-fluid pt-4 px-4">
    <div class="bg-secondary text-light rounded p-4">
        <h4 class="mb-4">Leave Requests</h4>

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
                        <td>${req.employeeName}</td>
                        <td>${req.startDate}</td>
                        <td>${req.endDate}</td>
                        <td>${req.reason}</td>
                        <td>${req.status}</td>
                        <td>${req.processedByName}</td>
                        <td>${req.processedReason}</td>
                        <td>
                            <c:if test="${canProcess and req.status == 'Inprogress'}">
                                <a href="approval?requestId=${req.requestId}" class="btn btn-sm btn-warning">
                                    Process
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
