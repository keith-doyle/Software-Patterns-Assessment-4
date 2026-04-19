<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.clothesstore.model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Products - ClothesStore</title>
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
        a.button {
            display: inline-block;
            padding: 8px 12px;
            margin-bottom: 15px;
            background: #1e88e5;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .actions a {
            margin-right: 10px;
        }
        form {
            margin-top: 8px;
        }
    </style>
</head>
<body>
    <h1>Admin Product Management</h1>

    <a class="button" href="<%= request.getContextPath() %>/admin/products/add">Add New Product</a>

    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
    %>

    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Category</th>
            <th>Manufacturer</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Active</th>
            <th>Actions / Replenish</th>
        </tr>

        <%
            if (products != null) {
                for (Product product : products) {
        %>
        <tr>
            <td><%= product.getProductId() %></td>
            <td><%= product.getTitle() %></td>
            <td><%= product.getCategoryName() %></td>
            <td><%= product.getManufacturerName() %></td>
            <td>€<%= product.getPrice() %></td>
            <td><%= product.getStockQuantity() %></td>
            <td><%= product.isActive() %></td>
            <td class="actions">
                <a href="<%= request.getContextPath() %>/admin/products/edit?id=<%= product.getProductId() %>">Edit</a>
                <a href="<%= request.getContextPath() %>/admin/products/delete?id=<%= product.getProductId() %>"
                   onclick="return confirm('Are you sure you want to delete this product?');">Delete</a>

                <form method="post" action="<%= request.getContextPath() %>/admin/products/replenish">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <input type="number" name="quantity" min="1" placeholder="Qty" required style="width:80px;">
                    <button type="submit">Replenish</button>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>