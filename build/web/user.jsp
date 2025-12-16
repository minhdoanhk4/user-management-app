<%-- 
    Document   : user
    Created on : Dec 2, 2025, 3:32:33 PM
    Author     : msi2k
--%>

<%@page import="fa3w.users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>User Page</title>
        <link rel="stylesheet" href="css/userStyle.css"/>

    </head>
    <body>

        <c:if test="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'US'}">
            <c:redirect url = "login.jsp"/>
        </c:if>
        <div class="container">
            <div class="header-user">
                <div class="welcome-text">
                    Welcome, <span>${sessionScope.LOGIN_USER.fullName}</span>
                </div>

                <form action="MainController">
                    <input type="submit" value="Logout" name="action" class="logout-btn" />
                </form>
            </div>

            <h1 class="page-title">My Profile Information</h1>

            <div class="user-card"> 
                <p>
                    <label>User ID:</label> <span>${sessionScope.LOGIN_USER.userID}</span>
                </p>
                <p>
                    <label>Full Name:</label> <span>${sessionScope.LOGIN_USER.fullName}</span>
                </p>
                <p>
                    <label>Role ID:</label> <span>${sessionScope.LOGIN_USER.roleID}</span>
                </p>
                <p>
                    <label>Password:</label> <span>${sessionScope.LOGIN_USER.password}</span>
                </p>
            </div> 
            <div class ="btn-shopping">
                <form action="MainController" method="POST">
                    <input type="submit" value="Shopping Page" name="action" class="shopping" />
                </form>
            </div>
            <div class="footer">
                Copyright &copy; 2025 User Management 3W. All rights reserved.
            </div>
        </div>
    </body>
</html>
