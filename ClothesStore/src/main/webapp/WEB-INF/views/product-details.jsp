<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.clothesstore.model.Product" %>
<%@ page import="com.clothesstore.model.Review" %>
<%@ page import="com.clothesstore.model.User" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Details - ClothesStore</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .product-box, .review-box, .review-form {
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 20px;
        }
        textarea, select {
            width: 100%;
            padding: 8px;
            margin: 8px 0 16px;
            box-sizing: border-box;
        }
        button {
            padding: 10px 16px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <%
        Product product = (Product) request.getAttribute("product");
        List<Review> reviews = (List<Review>) request.getAttribute("reviews");
        User loggedInUser = (User) session.getAttribute("loggedInUser");
    %>

    <%
        if (product != null) {
    %>
        <div class="product-box">
            <h1><%= product.getTitle() %></h1>
            <p><strong>Category:</strong> <%= product.getCategoryName() %></p>
            <p><strong>Manufacturer:</strong> <%= product.getManufacturerName() %></p>
            <p><strong>Description:</strong> <%= product.getDescription() %></p>
            <p><strong>Price:</strong> €<%= product.getPrice() %></p>
            <p><strong>Stock:</strong> <%= product.getStockQuantity() %></p>
            <p><strong>Average Rating:</strong> <%= product.getAverageRating() %></p>
        </div>

        <%
            if (loggedInUser != null && "CUSTOMER".equals(loggedInUser.getRole())) {
        %>
            <div class="review-form">
                <h2>Leave a Review</h2>
                <form method="post" action="<%= request.getContextPath() %>/add-review">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">

                    <label>Rating</label>
                    <select name="rating" required>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>

                    <label>Comment</label>
                    <textarea name="comment" rows="4"></textarea>

                    <button type="submit">Submit Review</button>
                </form>
            </div>
        <%
            }
        %>

        <h2>Reviews</h2>

        <%
            if (reviews != null && !reviews.isEmpty()) {
                for (Review review : reviews) {
        %>
            <div class="review-box">
                <p><strong><%= review.getReviewerName() %></strong></p>
                <p><strong>Rating:</strong> <%= review.getRating() %>/5</p>
                <p><%= review.getComment() %></p>
                <p><small><%= review.getCreatedAt() %></small></p>
            </div>
        <%
                }
            } else {
        %>
            <p>No reviews yet.</p>
        <%
            }
        %>
    <%
        } else {
    %>
        <p>Product not found.</p>
    <%
        }
    %>

    <p><a href="<%= request.getContextPath() %>/products">Back to Products</a></p>
</body>
</html>