package com.clothesstore.controller;

import com.clothesstore.model.Order;
import com.clothesstore.model.User;
import com.clothesstore.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/customer-orders")
public class AdminCustomerOrdersServlet extends HttpServlet {

    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = Integer.parseInt(request.getParameter("userId"));

        User customer = customerService.getUserById(userId);
        List<Order> orders = customerService.getOrdersForAdminByUserId(userId);

        request.setAttribute("customer", customer);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/views/admin-customer-orders.jsp").forward(request, response);
    }
}