<%-- 
    Document   : employer-header
    Created on : Sep 25, 2025, 2:57:47 AM
    Author     : shiro
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- CSS here -->
        <link rel="stylesheet" href="css/employer.css">
    </head>

    <body>
        <!-- Header Start -->
        <header class="header-area">
            <div class="container-fluid">
                <div class="header_bottom_border">
                    <div class="logo">
                        <a href="index.jsp">
                            <img src="img/logo.png" alt="">
                        </a>
                    </div>
                    <nav class="main-menu">
                        <ul>
                            <li ><a href="job_post.jsp">Create Job</a></li>
                            <li><a href="jobs">View Jobs</a></li>
                            <li><a href="viewApply">View Apply</a></li>
                        </ul>
                    </nav>

                </div>
            </div>
        </header>
    </body>


</html>
