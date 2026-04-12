package com.clothesstore.controller;

import com.clothesstore.model.Product;
import com.clothesstore.model.User;
import com.clothesstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/products")
public class AdminProductListServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<Product> products = productService.getAllProductsForAdmin();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/views/admin-products.jsp").forward(request, response);
    }
}