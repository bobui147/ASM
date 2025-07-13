<%
    request.setAttribute("pageContent", "leave-request-content.jsp");
    RequestDispatcher rd = request.getRequestDispatcher("main.jsp");
    rd.forward(request, response);
%>
