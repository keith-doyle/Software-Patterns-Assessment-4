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
        form.replenish-form {
            margin-top: 8px;
        }
        form.filter-form {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #ccc;
            border-radius: 8px;
        }
        input, select, button {
            margin: 8px 10px 8px 0;
            padding: 8px;
        }
        img.product-thumb {
            max-width: 60px;
            max-height: 60px;
            display: block;
        }
    </style>
</head>
<body>
    <h1>Admin Product Management</h1>

    <a class="button" href="<%= request.getContextPath() %>/admin/products/add">Add New Product</a>

    <%
        String selectedTitle = (String) request.getAttribute("selectedTitle");
        String selectedCategory = (String) request.getAttribute("selectedCategory");
        String selectedManufacturer = (String) request.getAttribute("selectedManufacturer");
        String selectedSort = (String) request.getAttribute("selectedSort");

        List<String> categories = (List<String>) request.getAttribute("categories");
        List<String> manufacturers = (List<String>) request.getAttribute("manufacturers");
        List<Product> products = (List<Product>) request.getAttribute("products");
    %>

    <form class="filter-form" method="get" action="<%= request.getContextPath() %>/admin/products">
        <label>Title:</label>
        <input type="text" name="title" value="<%= selectedTitle != null ? selectedTitle : "" %>">

        <label>Category:</label>
        <select name="category">
            <option value="">All Categories</option>
            <%
                if (categories != null) {
                    for (String category : categories) {
            %>
                <option value="<%= category %>" <%= category.equals(selectedCategory) ? "selected" : "" %>>
                    <%= category %>
                </option>
            <%
                    }
                }
            %>
        </select>

        <label>Manufacturer:</label>
        <select name="manufacturer">
            <option value="">All Manufacturers</option>
            <%
                if (manufacturers != null) {
                    for (String manufacturer : manufacturers) {
            %>
                <option value="<%= manufacturer %>" <%= manufacturer.equals(selectedManufacturer) ? "selected" : "" %>>
                    <%= manufacturer %>
                </option>
            <%
                    }
                }
            %>
        </select>

        <label>Sort By:</label>
        <select name="sort">
            <option value="titleAsc" <%= "titleAsc".equals(selectedSort) || selectedSort == null ? "selected" : "" %>>Title A-Z</option>
            <option value="titleDesc" <%= "titleDesc".equals(selectedSort) ? "selected" : "" %>>Title Z-A</option>
            <option value="priceAsc" <%= "priceAsc".equals(selectedSort) ? "selected" : "" %>>Price Low-High</option>
            <option value="priceDesc" <%= "priceDesc".equals(selectedSort) ? "selected" : "" %>>Price High-Low</option>
            <option value="manufacturerAsc" <%= "manufacturerAsc".equals(selectedSort) ? "selected" : "" %>>Manufacturer A-Z</option>
            <option value="manufacturerDesc" <%= "manufacturerDesc".equals(selectedSort) ? "selected" : "" %>>Manufacturer Z-A</option>
        </select>

        <button type="submit">Apply</button>
    </form>

    <table>
        <tr>
            <th>ID</th>
            <th>Image</th>
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
            <td>
                <img class="product-thumb"
                     src="<%= request.getContextPath() %>/<%= product.getImagePath() %>"
                     alt="<%= product.getTitle() %>">
            </td>
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

                <form class="replenish-form" method="post" action="<%= request.getContextPath() %>/admin/products/replenish">
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