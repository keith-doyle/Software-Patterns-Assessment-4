<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.clothesstore.model.Order" %>
<%@ page import="com.clothesstore.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Order History - ClothesStore</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <%
        User customer = (User) request.getAttribute("customer");
        List<Order> orders = (List<Order>) request.getAttribute("orders");
    %>

    <h1>Order History for <%= customer != null ? customer.getFullName() : "Customer" %></h1>

    <%
        if (orders != null && !orders.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Order ID</th>
                <th>Total Price</th>
                <th>Status</th>
                <th>Created At</th>
            </tr>
            <%
                for (Order order : orders) {
            %>
                <tr>
                    <td><%= order.getOrderId() %></td>
                    <td>€<%= order.getTotalPrice() %></td>
                    <td><%= order.getStatus() %></td>
                    <td><%= order.getCreatedAt() %></td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <p>This customer has no orders.</p>
    <%
        }
    %>

    <p><a href="<%= request.getContextPath() %>/admin/customers">Back to Customers</a></p>
</body>
</html>