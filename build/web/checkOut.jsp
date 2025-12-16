<%-- 
    Document   : checkOut
    Created on : Dec 12, 2025, 10:48:11 PM
    Author     : msi2k
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="fa3w.products.ProductDTO"%>
<%@page import="fa3w.products.Cart"%>
<%@page import="fa3w.users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>Checkout Page</title>
        <link rel="stylesheet" href="css/checkoutStyle.css"/>
    </head>
    <body>
        <h1>Check Out Your Cart</h1>
        <c:if test="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'US'}">
            <c:redirect url="login.jsp"/>
        </c:if>

        <c:if test="${not empty requestScope.ERROR}">
            <font color="red">
            ${requestScope.ERROR}
            </font>
        </c:if>

        <c:set var="cart" value="${sessionScope.CART}"/>
        <c:choose>
            <c:when test="${not empty cart and not empty cart.cart and cart.cart.size() > 0}">
                <c:set var="totalAmount" value="0"/>
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${cart.cart.values()}" varStatus="counter">
                            <c:set var="itemTotal" value="${product.quantity * product.price}"/>
                            <c:set var="totalAmount" value="${totalAmount + itemTotal}"/>
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${product.productID}
                                </td>
                                <td>
                                    ${product.name}
                                </td>
                                <td>
                                    <fmt:formatNumber value="${product.price}" pattern="#,##0.00"/>
                                </td>
                                <td>
                                    ${product.quantity}
                                </td>
                                <td>
                                    <fmt:formatNumber value="${itemTotal}" pattern="#,##0.00"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <h2>
                    Total Amount: <fmt:formatNumber value="${totalAmount}" pattern="#,##0.00"/>$
                </h2>

                <form action="MainController" method="POST">
                    <input type="submit" value="Confirm" name="action"/>
                </form>

                <form action="MainController">
                    <input type="submit" value="View Cart" name="action" />
                </form>
                
            </c:when>
            <c:otherwise>
                <h2>Your cart is empty. <a href="shopping.jsp">Go shopping</a></h2>
            </c:otherwise>
        </c:choose>
    </body>
</html>
