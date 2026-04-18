<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.clothesstore.model.CartItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Cart - ClothesStore</title>
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
        .summary {
            margin-top: 20px;
            font-size: 18px;
        }
        form.inline {
            display: inline;
        }
        a.button, button.checkout-btn {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 14px;
            background: #1e88e5;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h1>Your Shopping Cart</h1>

    <%
        List<CartItem> cart = (List<CartItem>) request.getAttribute("cart");
        Double subtotal = (Double) request.getAttribute("subtotal");
        Double discountAmount = (Double) request.getAttribute("discountAmount");
        Double finalTotal = (Double) request.getAttribute("finalTotal");
        String discountDescription = (String) request.getAttribute("discountDescription");
    %>

    <%
        if (cart != null && !cart.isEmpty()) {
    %>
        <table>
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Subtotal</th>
                <th>Actions</th>
            </tr>

            <%
                for (CartItem item : cart) {
            %>
                <tr>
                    <td><%= item.getProduct().getTitle() %></td>
                    <td>€<%= item.getProduct().getPrice() %></td>
                    <td>
                        <form class="inline" method="post" action="<%= request.getContextPath() %>/cart/update">
                            <input type="hidden" name="productId" value="<%= item.getProduct().getProductId() %>">
                            <input type="number" name="quantity" min="0" value="<%= item.getQuantity() %>" style="width: 70px;">
                            <button type="submit">Update</button>
                        </form>
                    </td>
                    <td>€<%= item.getSubtotal() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/cart/remove?productId=<%= item.getProduct().getProductId() %>">Remove</a>
                    </td>
                </tr>
            <%
                }
            %>
        </table>

        <div class="summary">
            <p><strong>Subtotal:</strong> €<%= subtotal %></p>
            <p><strong>Discount logic:</strong> <%= discountDescription %></p>
            <p><strong>Discount amount:</strong> €<%= discountAmount %></p>
            <p><strong>Final Total:</strong> €<%= finalTotal %></p>
        </div>

        <form method="post" action="<%= request.getContextPath() %>/checkout">
            <button class="checkout-btn" type="submit">Checkout</button>
        </form>
    <%
        } else {
    %>
        <p>Your cart is empty.</p>
    <%
        }
    %>

    <a class="button" href="<%= request.getContextPath() %>/products">Continue Shopping</a>
</body>
</html>