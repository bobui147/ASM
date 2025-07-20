<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.leave.leavemanagementweb.model.AgendaRow" %>
<%
    List<String> dates = (List<String>) request.getAttribute("dates");
    List<AgendaRow> rows = (List<AgendaRow>) request.getAttribute("rows");
    String startDate = request.getParameter("startDate") != null ? request.getParameter("startDate") : "2025-01-01";
%>
<div class="container-fluid pt-4 px-4">
    <div class="row g-4">
        <div class="col-12">
            <div class="bg-secondary text-light rounded p-4">
                <h3 class="mb-4">Agenda</h3>
                <form method="get" action="agenda" class="mb-4 d-flex align-items-center gap-2">
                    <label for="startDate" class="form-label mb-0 me-2">From date:</label>
                    <input type="date" id="startDate" name="startDate" class="form-control" style="max-width: 200px;" value="<%= startDate %>" required />
                    <button type="submit" class="btn btn-primary ms-2">Filter</button>
                </form>
                <div class="table-responsive">
                    <table class="table table-dark table-hover align-middle table-bordered text-center mb-0">
                        <thead>
                            <tr>
                                <th class="align-middle">Employee</th>
                                <% if (dates != null) for(String d : dates) { %>
                                    <th class="align-middle"><%= d %></th>
                                <% } %>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (rows != null) for(AgendaRow row : rows) { %>
                                <tr>
                                    <td class="align-middle"><%= row.getEmployeeName() %></td>
                                    <% for(String status : row.getStatuses()) { 
                                        String color = "OnLeave".equals(status) ? "#ff4444" : ("Working".equals(status) ? "#90ee90" : ""); %>
                                        <td class="align-middle" <%= color.isEmpty() ? "" : "style='background-color:" + color + ";'" %> ></td>
                                    <% } %>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div> 