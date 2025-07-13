<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.leave.leavemanagementweb.model.User" %>
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
        <title>Dashboard | Leave Management</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">

        <!-- Icon Font Stylesheet -->
        <link href="lib/fontawesome/css/all.min.css" rel="stylesheet">
        <link href="lib/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="lib/tempusdominus/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet" />

        <!-- Bootstrap & Template Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <div class="container-fluid position-relative d-flex p-0">

            <!-- Sidebar Start -->
            <div class="sidebar pe-4 pb-3 bg-dark">
                <nav class="navbar bg-dark navbar-dark">
                    <a href="#" class="navbar-brand mx-4 mb-3">
                        <h3 class="text-primary"><i class="fas fa-user-edit me-2"></i>DarkPan</h3>
                    </a>
                    <div class="navbar-nav w-100">
                        <a href="dashboard.jsp" class="nav-item nav-link active"><i class="fa fa-tachometer-alt me-2"></i>Dashboard</a>
                        <!-- You can add more menu items here -->
                    </div>
                </nav>
            </div>
            <!-- Sidebar End -->

            <!-- Content Start -->
            <div class="content">
                <!-- Navbar Start -->
                <nav class="navbar navbar-expand bg-secondary navbar-dark sticky-top px-4 py-0">
                    <a href="#" class="navbar-brand d-flex d-lg-none me-4">
                        <h2 class="text-primary mb-0"><i class="fa fa-user-edit"></i></h2>
                    </a>
                    <div class="navbar-nav align-items-center ms-auto">
                        <div class="nav-item">
                            <a href="login.jsp" class="nav-link text-danger"><i class="fas fa-sign-out-alt"></i> Logout</a>
                        </div>
                    </div>
                </nav>
                <!-- Navbar End -->

                <!-- Main Content -->
                <div class="container-fluid pt-4 px-4">
                    <div class="bg-secondary text-light rounded p-4">
                        <h2 class="mb-4">Welcome, <%= user.getFullName() %>!</h2>
                        <p><strong>Username:</strong> <%= user.getUsername() %></p>
                        <p><strong>User ID:</strong> <%= user.getUserId() %></p>
                    </div>
                </div>
            </div>
            <!-- Content End -->
        </div>

        <!-- JavaScript Libraries -->
        <script src="lib/jquery/jquery.min.js"></script>
        <script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="lib/chart/chart.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="lib/tempusdominus/js/moment.min.js"></script>
        <script src="lib/tempusdominus/js/tempusdominus-bootstrap-4.min.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>
</html>

