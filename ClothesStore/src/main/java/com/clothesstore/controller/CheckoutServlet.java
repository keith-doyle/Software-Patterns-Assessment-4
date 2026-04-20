package com.clothesstore.controller;

import com.clothesstore.model.CartItem;
import com.clothesstore.model.User;
import com.clothesstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");

        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        int orderId = orderService.checkout(loggedInUser.getUserId(), cart);

        if (orderId != -1) {
            session.removeAttribute("cart");
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/WEB-INF/views/order-success.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }
}