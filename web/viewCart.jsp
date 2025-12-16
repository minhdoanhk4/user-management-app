<%-- 
    Document   : viewCart
    Created on : Dec 11, 2025, 11:22:25 PM
    Author     : msi2k
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="fa3w.products.Cart"%>
<%@page import="fa3w.users.UserDTO"%>
<%@page import="fa3w.products.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>Cart Page</title>
        <link rel="stylesheet" href="css/cartStyle.css"/>
    </head>
    <body>
        <c:if test="${sessionScope.LOGIN_USER == null || sessionScope.LOGIN_USER.roleID ne 'US'}">
            <c:redirect url = "login.jsp"/>
        </c:if>

        <div class="container">
            <div class="cart-header-bar">
                <h1 class="page-title">Your Shopping Cart</h1>
                <a href="MainController?action=Search+Product" class="shopping-btn">
                    <span>&#8592;</span> Continue Shopping
                </a>
            </div>
            <c:if test="${not empty requestScope.ERROR}">
                <div style="background-color: #fee2e2; color: #ef4444; padding: 15px; border-radius: 8px; margin-bottom: 20px; border: 1px solid #fecaca;">
                    ${requestScope.ERROR}
                </div>
            </c:if>

            <c:set var="cart" value="${sessionScope.CART}"/>
            <c:choose>
                <c:when test="${not empty cart and not empty cart.cart and cart.cart.size() > 0}">
                    <c:set var="totalAmount" value="0"/>
                    <table class="cart-table">
                        <thead>
                            <tr>
                                <th style="width: 5%">No.</th>
                                <th style="width: 15%">ID</th>
                                <th style="width: 30%; text-align: left; padding-left: 20px;">Product Name</th>
                                <th style="width: 15%">Price</th>
                                <th style="width: 10%">Qty</th>
                                <th style="width: 15%">Total</th>
                                <th style="width: 10%" colspan="2">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="product" items="${cart.cart.values()}" varStatus="counter">
                                <c:set var="itemTotal" value="${product.price * product.quantity}"/>
                                <c:set var="totalAmount" value="${totalAmount + itemTotal}"/>
                            <form action="MainController" method="POST">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td style="font-family: monospace; color: #64748b;">
                                        ${product.productID}
                                    </td>
                                    <td style="text-align: left; padding-left: 20px; font-weight: 600;">
                                        ${product.name}
                                    </td>
                                    <td>
                                        $<fmt:formatNumber value="${product.price}" pattern="#,##0.00"/>
                                    </td>
                                    <td>
                                        <input type="number" name="quantity" value="${product.quantity}" required="" min="1" class="quantity-input"/>
                                    </td>
                                    <td style="color: var(--primary); font-weight: 700;">
                                        $<fmt:formatNumber value="${itemTotal}" pattern="#,##0.00"/>
                                    </td>

                                    <td style="padding: 5px;">
                                        <input type="hidden" name="id" value="${product.productID}"/>
                                        <input type="submit" value="Edit" name="action" class="edit-btn"/>
                                    </td>
                                    <td style="padding: 5px;">
                                        <input type="submit" value="Remove" name="action" class="remove-btn"/>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>

                    <div class="cart-footer-bar">
                        <div class="cart-summary">
                            <p>Total Amount: 
                                <span class="total-amount">
                                    $<fmt:formatNumber value="${totalAmount}" pattern="#,##0.00"/>
                                </span>
                            </p>
                        </div>

                        <form action="MainController">
                            <input type="submit" value="Check Out" name="action" class="checkout-btn" />
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="empty-cart-message">
                        <img src="https://cdn-icons-png.flaticon.com/512/11329/11329060.png" alt="Empty Cart" style="width: 100px; opacity: 0.5; margin-bottom: 20px;">
                        <p>Your cart is currently empty.</p>
                        <a href="MainController?action=Search+Product" style="color: var(--primary); text-decoration: none; font-weight: 600;">Go to Store</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
