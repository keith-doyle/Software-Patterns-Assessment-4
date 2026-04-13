<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.clothesstore.model.Product" %>
<%@ page import="com.clothesstore.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Products - ClothesStore</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }

        h1 {
            margin-bottom: 20px;
        }

        form.filter-form {
            margin-bottom: 30px;
            padding: 15px;
            border: 1px solid #ccc;
            border-radius: 8px;
        }

        input, select, button {
            margin: 8px 10px 8px 0;
            padding: 8px;
        }

        .product-card {
            border: 1px solid #ccc;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 8px;
        }

        .product-title {
            font-size: 20px;
            font-weight: bold;
        }

        .product-meta {
            color: #555;
            margin: 8px 0;
        }

        .product-price {
            color: #0a7a2f;
            font-weight: bold;
            font-size: 20px;
        }

        .cart-form {
            margin-top: 12px;
        }
    </style>
</head>
<body>

    <%
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        String selectedTitle = (String) request.getAttribute("selectedTitle");
        String selectedCategory = (String) request.getAttribute("selectedCategory");
        String selectedManufacturer = (String) request.getAttribute("selectedManufacturer");
        String selectedSort = (String) request.getAttribute("selectedSort");

        List<String> categories = (List<String>) request.getAttribute("categories");
        List<String> manufacturers = (List<String>) request.getAttribute("manufacturers");
    %>

    <h1>Product Catalogue</h1>

    <form class="filter-form" method="get" action="<%= request.getContextPath() %>/products">
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
        </select>

        <button type="submit">Apply</button>
    </form>

    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
    %>
        <div class="product-card">
            <div class="product-title"><%= product.getTitle() %></div>
            <div class="product-meta">
                Category: <%= product.getCategoryName() %> |
                Manufacturer: <%= product.getManufacturerName() %>
            </div>
            <div><%= product.getDescription() %></div>
            <div class="product-meta">Stock: <%= product.getStockQuantity() %></div>
            <div class="product-meta">Rating: <%= product.getAverageRating() %></div>
            <div class="product-price">€<%= product.getPrice() %></div>

            <%
                if (loggedInUser != null && "CUSTOMER".equals(loggedInUser.getRole())) {
            %>
                <form class="cart-form" method="post" action="<%= request.getContextPath() %>/cart/add">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button type="submit">Add to Cart</button>
                </form>
            <%
                }
            %>
        </div>
    <%
            }
        } else {
    %>
        <p>No products found.</p>
    <%
        }
    %>

</body>
</html>