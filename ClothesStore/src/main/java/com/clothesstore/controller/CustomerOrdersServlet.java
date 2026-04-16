package com.clothesstore.controller;

import com.clothesstore.model.Order;
import com.clothesstore.model.User;
import com.clothesstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/my-orders")
public class CustomerOrdersServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null || !"CUSTOMER".equals(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Order> orders = orderService.getOrdersByUserId(loggedInUser.getUserId());
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/views/customer-orders.jsp").forward(request, response);
    }
}