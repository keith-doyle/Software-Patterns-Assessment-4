package com.clothesstore.controller;

import com.clothesstore.model.User;
import com.clothesstore.pattern.command.Command;
import com.clothesstore.pattern.command.ReplenishStockCommand;
import com.clothesstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/products/replenish")
public class ReplenishStockServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if (quantity > 0) {
            Command replenishCommand = new ReplenishStockCommand(productService, productId, quantity);
            replenishCommand.execute();
        }

        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}