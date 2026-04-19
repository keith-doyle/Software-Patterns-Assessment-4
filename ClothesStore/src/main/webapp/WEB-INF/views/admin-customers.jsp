<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.clothesstore.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Customers - ClothesStore</title>
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
    <h1>Customer Management</h1>

    <%
        List<User> customers = (List<User>) request.getAttribute("customers");
        if (customers != null && !customers.isEmpty()) {
    %>
        <table>
            <tr>
                <th>User ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Address</th>
                <th>Payment Method</th>
                <th>Loyalty Points</th>
                <th>Created At</th>
                <th>Orders</th>
            </tr>
            <%
                for (User customer : customers) {
            %>
                <tr>
                    <td><%= customer.getUserId() %></td>
                    <td><%= customer.getFullName() %></td>
                    <td><%= customer.getEmail() %></td>
                    <td><%= customer.getAddress() %></td>
                    <td><%= customer.getPaymentMethod() %></td>
                    <td><%= customer.getLoyaltyPoints() %></td>
                    <td><%= customer.getCreatedAt() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/admin/customer-orders?userId=<%= customer.getUserId() %>">
                            View Orders
                        </a>
                    </td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <p>No customers found.</p>
    <%
        }
    %>
</body>
</html>