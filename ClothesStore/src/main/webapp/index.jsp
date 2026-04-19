<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.clothesstore.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ClothesStore</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        nav a, nav span { margin-right: 15px; }
    </style>
</head>
<body>
    <%
        User loggedInUser = (User) session.getAttribute("loggedInUser");
    %>

    <h1>Welcome to ClothesStore</h1>

    <nav>
        <a href="<%= request.getContextPath() %>/products">View Products</a>

        <%
            if (loggedInUser == null) {
        %>
            <a href="<%= request.getContextPath() %>/login">Login</a>
            <a href="<%= request.getContextPath() %>/register">Register</a>
        <%
            } else {
        %>
            <span>
                Hello, <%= loggedInUser.getFullName() %> (<%= loggedInUser.getRole() %>)
                <%
                    if ("CUSTOMER".equals(loggedInUser.getRole())) {
                %>
                    - Loyalty Points: <%= loggedInUser.getLoyaltyPoints() %>
                <%
                    }
                %>
            </span>

            <%
                if ("ADMIN".equals(loggedInUser.getRole())) {
            %>
                <a href="<%= request.getContextPath() %>/admin/products">Admin Products</a>
                <a href="<%= request.getContextPath() %>/admin/orders">Admin Orders</a>
                <a href="<%= request.getContextPath() %>/admin/customers">Admin Customers</a>
            <%
                }
                if ("CUSTOMER".equals(loggedInUser.getRole())) {
            %>
                <a href="<%= request.getContextPath() %>/cart">My Cart</a>
                <a href="<%= request.getContextPath() %>/my-orders">My Orders</a>
            <%
                }
            %>

            <a href="<%= request.getContextPath() %>/logout">Logout</a>
        <%
            }
        %>
    </nav>
</body>
</html>