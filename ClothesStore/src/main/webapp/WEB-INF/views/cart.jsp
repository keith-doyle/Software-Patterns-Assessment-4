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
        .total {
            margin-top: 20px;
            font-size: 20px;
            font-weight: bold;
        }
        form.inline {
            display: inline;
        }
        a.button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 14px;
            background: #1e88e5;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <h1>Your Shopping Cart</h1>

    <%
        List<CartItem> cart = (List<CartItem>) request.getAttribute("cart");
        Double total = (Double) request.getAttribute("total");
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

        <div class="total">Total: €<%= total %></div>
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