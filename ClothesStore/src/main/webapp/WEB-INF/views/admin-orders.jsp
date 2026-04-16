<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.clothesstore.model.Order" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Orders - ClothesStore</title>
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
            vertical-align: top;
        }
        th {
            background-color: #f2f2f2;
        }
        form {
            margin: 0;
        }
    </style>
</head>
<body>
    <h1>Admin Order Management</h1>

    <%
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders != null && !orders.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Order ID</th>
                <th>User ID</th>
                <th>Total Price</th>
                <th>Status</th>
                <th>Created At</th>
                <th>Update Status</th>
            </tr>
            <%
                for (Order order : orders) {
            %>
                <tr>
                    <td><%= order.getOrderId() %></td>
                    <td><%= order.getUserId() %></td>
                    <td>€<%= order.getTotalPrice() %></td>
                    <td><%= order.getStatus() %></td>
                    <td><%= order.getCreatedAt() %></td>
                    <td>
                        <%
                            if (!order.getAllowedTransitions().isEmpty()) {
                        %>
                            <form method="post" action="<%= request.getContextPath() %>/admin/orders/update-status">
                                <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                                <select name="newStatus">
                                    <%
                                        for (String status : order.getAllowedTransitions()) {
                                    %>
                                        <option value="<%= status %>"><%= status %></option>
                                    <%
                                        }
                                    %>
                                </select>
                                <button type="submit">Update</button>
                            </form>
                        <%
                            } else {
                        %>
                            No further transitions
                        <%
                            }
                        %>
                    </td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <p>No orders found.</p>
    <%
        }
    %>
</body>
</html>