<%
    request.setAttribute("pageContent", "leave-list-content.jsp");
    RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
    rd.forward(request, response);
%>
