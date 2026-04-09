<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.clothesstore.model.Product" %>
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
        }
    </style>
</head>
<body>
    <h1>Product Catalogue</h1>

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