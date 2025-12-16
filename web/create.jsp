<%-- 
    Document   : create
    Created on : Dec 5, 2025, 6:51:37 AM
    Author     : msi2k
--%>

<%@page import="fa3w.users.UserError"%>
<%@page import="fa3w.users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>SIGN UP</title>
        <link rel="stylesheet" href="css/registrationStyle.css"/>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body>
        <div class="container">
            <div class="boxReset">
                <h1>Sign Up</h1>
                <form action="MainController" method="POST">
                    <p>User ID *
                        <input type="text" name="txtUserID" value="${param.txtUserID}" required="" 
                               placeholder="Enter your User ID"/>
                        <font color="red">${requestScope.USER_ERROR.userIDError}</font>
                    </p>
                    <p>Full Name *
                        <input type="text" name="txtFullName" value="${param.txtFullName}" required="" 
                               placeholder="Enter your Full Name"/>
                        <font color="red">${requestScope.USER_ERROR.fullNameError}</font>
                    </p>
                    <p>New Password *
                        <input type="password" name="txtPassword" value="" required="" 
                               placeholder="Enter new password"/>
                    </p>
                    <p>Confirm Password *
                        <input type="password" name="txtConfirmPass" value="" required="" 
                               placeholder="Confirm new password"/>
                        <font color="red">${requestScope.USER_ERROR.confirmError}</font>
                    </p>
                    <p>Role ID *
                        <input type="text" name="txtRoleID" value="${param.txtRoleID}" required="" 
                               placeholder="Enter your Role ID"/>
                    </p>

                    <div class="g-recaptcha" data-sitekey="6LfI_CEsAAAAAAtyYU9q44iS1M9E_yV4-jxZMQoD"></div>

                    <font color="red">
                    ${requestScope.ERROR}
                    </font>
                    <div class="warning-text">
                        <font>
                        <font style="font-weight: bolder">Warning!</font> 
                        Do not share your information with anyone because it is private. 
                        We are not responsible if you share it with others. Please protect your information!
                        </font>
                    </div>

                    <div class="btn-container">
                        <input type="submit" value="Sign Up" name="action" />
                        <input type="button" value="Cancel" onclick="window.location.href = 'login.jsp'" />
                    </div>
                </form>

                <font color="red">
                ${requestScope.USER_ERROR.error}
                </font>
            </div>

            <div class="footer">
                Copyright &copy; 2025 User Management 3W. All rights reserved.
            </div>
        </div>
    </body>
</html>
