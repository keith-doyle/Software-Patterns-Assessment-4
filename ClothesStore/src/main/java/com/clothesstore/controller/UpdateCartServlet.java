package com.clothesstore.controller;

import com.clothesstore.model.CartItem;
import com.clothesstore.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@WebServlet("/cart/update")
public class UpdateCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            Iterator<CartItem> iterator = cart.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getProduct().getProductId() == productId) {
                    if (quantity <= 0) {
                        iterator.remove();
                    } else {
                        item.setQuantity(quantity);
                    }
                    break;
                }
            }
            session.setAttribute("cart", cart);
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }
}