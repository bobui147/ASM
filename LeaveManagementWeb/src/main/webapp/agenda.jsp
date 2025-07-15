<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.leave.leavemanagementweb.model.AgendaRow" %>
<%
    List<String> dates = (List<String>) request.getAttribute("dates");
    List<AgendaRow> rows = (List<AgendaRow>) request.getAttribute("rows");
    String year = "2025";
%>
<div class="container-fluid pt-4 px-4">
    <div class="row g-4">
        <div class="col-12">
            <div class="bg-secondary text-light rounded p-4">
                <h3 class="mb-4">Agenda</h3>
                <div class="table-responsive">
                    <table class="table table-dark table-hover align-middle table-bordered text-center mb-0">
                        <thead>
                            <tr>
                                <th class="align-middle">Nhân sự</th>
                                <% if (dates != null) for(String d : dates) { %>
                                    <th class="align-middle"><%= d + "/" + year %></th>
                                <% } %>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (rows != null) for(AgendaRow row : rows) { %>
                                <tr>
                                    <td class="align-middle"><%= row.getEmployeeName() %></td>
                                    <% for(String status : row.getStatuses()) { 
                                        String color = "OnLeave".equals(status) ? "#ff4444" : "#90ee90"; %>
                                        <td class="align-middle" bgcolor="<%= color %>"></td>
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