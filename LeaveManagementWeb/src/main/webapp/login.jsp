<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Login | Leave Management</title>
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

    <style>
        body {
            background-color: #000 !important;
        }
    </style>
</head>

<body>
    <div class="container-fluid d-flex align-items-center justify-content-center vh-100">
        <div class="col-12 col-sm-8 col-md-6 col-lg-5 col-xl-4">
            <div class="bg-secondary rounded p-4 p-sm-5">
                <div class="text-center mb-4">
                    <h3 class="text-light">Sign In</h3>
                </div>
                <form action="login" method="post">
                    <div class="form-floating mb-3">
                        <input type="text" class="form-control bg-dark text-light" id="floatingInput" placeholder="Username" name="username" required>
                        <label for="floatingInput" class="text-muted">Email address</label>
                    </div>
                    <div class="form-floating mb-4">
                        <input type="password" class="form-control bg-dark text-light" id="floatingPassword" placeholder="Password" name="password" required>
                        <label for="floatingPassword" class="text-muted">Password</label>
                    </div>
                    <p style="color: red">${requestScope.error}</p>
                    <button type="submit" class="btn btn-danger py-3 w-100 mb-4">Sign In</button>
                </form>
            </div>
        </div>
    </div>

    <!-- JS Libraries -->
    <script src="lib/jquery/jquery.min.js"></script>
    <script src="lib/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="js/main.js"></script>
</body>
</html>
