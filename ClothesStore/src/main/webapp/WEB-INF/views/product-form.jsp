<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.clothesstore.model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= request.getAttribute("formTitle") %> - ClothesStore</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        form { max-width: 600px; }
        input, textarea, select {
            width: 100%;
            padding: 10px;
            margin: 8px 0 16px;
            box-sizing: border-box;
        }
        button {
            padding: 10px 16px;
            cursor: pointer;
        }
        .error {
            color: red;
            margin-bottom: 16px;
        }
    </style>
</head>
<body>
    <h1><%= request.getAttribute("formTitle") %></h1>

    <%
        Product product = (Product) request.getAttribute("product");
        List<String> categories = (List<String>) request.getAttribute("categories");
        List<String> manufacturers = (List<String>) request.getAttribute("manufacturers");
        String errorMessage = (String) request.getAttribute("errorMessage");
    %>

    <%
        if (errorMessage != null) {
    %>
        <div class="error"><%= errorMessage %></div>
    <%
        }
    %>

    <form method="post" action="<%= request.getAttribute("formAction") %>">
        <%
            if (product != null) {
        %>
            <input type="hidden" name="productId" value="<%= product.getProductId() %>">
        <%
            }
        %>

        <label>Title</label>
        <input type="text" name="title" required value="<%= product != null ? product.getTitle() : "" %>">

        <label>Description</label>
        <textarea name="description" rows="4"><%= product != null ? product.getDescription() : "" %></textarea>

        <label>Price</label>
        <input type="number" step="0.01" name="price" required value="<%= product != null ? product.getPrice() : "" %>">

        <label>Stock Quantity</label>
        <input type="number" name="stockQuantity" required value="<%= product != null ? product.getStockQuantity() : "" %>">

        <label>Image Path</label>
        <input type="text" name="imagePath" value="<%= product != null ? product.getImagePath() : "" %>">

        <label>Category</label>
        <select name="category" required>
            <%
                if (categories != null) {
                    for (String category : categories) {
                        boolean selected = product != null && category.equals(product.getCategoryName());
            %>
                <option value="<%= category %>" <%= selected ? "selected" : "" %>><%= category %></option>
            <%
                    }
                }
            %>
        </select>

        <label>Manufacturer</label>
        <select name="manufacturer" required>
            <%
                if (manufacturers != null) {
                    for (String manufacturer : manufacturers) {
                        boolean selected = product != null && manufacturer.equals(product.getManufacturerName());
            %>
                <option value="<%= manufacturer %>" <%= selected ? "selected" : "" %>><%= manufacturer %></option>
            <%
                    }
                }
            %>
        </select>

        <%
            if (product != null) {
        %>
            <label>
                <input type="checkbox" name="active" <%= product.isActive() ? "checked" : "" %>>
                Active
            </label>
        <%
            }
        %>

        <button type="submit">Save Product</button>
    </form>
</body>
</html>