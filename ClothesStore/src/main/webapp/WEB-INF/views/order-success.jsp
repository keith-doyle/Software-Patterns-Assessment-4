<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Successful - ClothesStore</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 14px;
            background: #1e88e5;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <h1>Order Placed Successfully</h1>
    <p>Your order has been created successfully.</p>
    <p>Order ID: <strong><%= request.getAttribute("orderId") %></strong></p>

    <a href="<%= request.getContextPath() %>/products">Continue Shopping</a>
</body>
</html>