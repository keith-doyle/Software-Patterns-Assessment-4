package com.clothesstore.controller;

import com.clothesstore.model.CartItem;
import com.clothesstore.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class ViewCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        double total = 0.0;
        for (CartItem item : cart) {
            total += item.getSubtotal();
        }

        request.setAttribute("cart", cart);
        request.setAttribute("total", total);
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }
}