<%
    request.setAttribute("pageContent", "dashboard-content.jsp");
    RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
    rd.forward(request, response);
%>
