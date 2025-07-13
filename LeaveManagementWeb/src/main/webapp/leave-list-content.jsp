<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.leave.leavemanagementweb.model.LeaveRequest" %>
<%@ page import="com.leave.leavemanagementweb.model.User" %>
<%
    User user = (User) session.getAttribute("user");
    List<LeaveRequest> requests = (List<LeaveRequest>) request.getAttribute("requests");
%>

<div class="container-fluid pt-4 px-4">
    <div class="bg-secondary text-light rounded p-4">
        <h3 class="mb-4">Leave Requests</h3>
        <table class="table table-dark table-hover">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Employee</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Reason</th>
                    <th>Status</th>
                    <th>Processed By</th>
                </tr>
            </thead>
            <tbody>
            <%
                int i = 1;
                for (LeaveRequest req : requests) {
            %>
                <tr>
                    <td><%= i++ %></td>
                    <td><%= req.getEmployeeName() %></td>
                    <td><%= req.getStartDate() %></td>
                    <td><%= req.getEndDate() %></td>
                    <td><%= req.getReason() %></td>
                    <td><%= req.getStatus() %></td>
                    <td><%= req.getProcessedByName() != null ? req.getProcessedByName() : "N/A" %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
