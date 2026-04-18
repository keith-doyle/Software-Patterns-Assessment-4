package com.clothesstore.controller;

import com.clothesstore.model.CartItem;
import com.clothesstore.model.User;
import com.clothesstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class ViewCartServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

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

        double subtotal = orderService.calculateCartSubtotal(cart);
        double finalTotal = orderService.calculateDiscountedTotal(loggedInUser.getUserId(), cart);
        double discountAmount = subtotal - finalTotal;
        String discountDescription = orderService.getDiscountDescription(loggedInUser.getUserId());

        request.setAttribute("cart", cart);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("discountAmount", discountAmount);
        request.setAttribute("finalTotal", finalTotal);
        request.setAttribute("discountDescription", discountDescription);

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }
}