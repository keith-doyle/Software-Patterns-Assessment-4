package com.clothesstore.controller;

import com.clothesstore.model.User;
import com.clothesstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/products/delete")
public class DeleteProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("id"));
        productService.deleteProduct(productId);

        response.sendRedirect(request.getContextPath() + "/admin/products");
    }
}