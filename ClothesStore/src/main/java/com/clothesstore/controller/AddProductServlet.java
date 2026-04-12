package com.clothesstore.controller;

import com.clothesstore.model.Product;
import com.clothesstore.model.User;
import com.clothesstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/products/add")
public class AddProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        request.setAttribute("categories", productService.getAllCategories());
        request.setAttribute("manufacturers", productService.getAllManufacturers());
        request.setAttribute("formAction", request.getContextPath() + "/admin/products/add");
        request.setAttribute("formTitle", "Add Product");
        request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null || !"ADMIN".equals(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Product product = new Product();
        product.setTitle(request.getParameter("title"));
        product.setDescription(request.getParameter("description"));
        product.setPrice(Double.parseDouble(request.getParameter("price")));
        product.setStockQuantity(Integer.parseInt(request.getParameter("stockQuantity")));
        product.setImagePath(request.getParameter("imagePath"));
        product.setAverageRating(0.0);
        product.setActive(true);

        String categoryName = request.getParameter("category");
        String manufacturerName = request.getParameter("manufacturer");

        boolean success = productService.addProduct(product, categoryName, manufacturerName);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        } else {
            request.setAttribute("errorMessage", "Failed to add product.");
            request.setAttribute("categories", productService.getAllCategories());
            request.setAttribute("manufacturers", productService.getAllManufacturers());
            request.setAttribute("formAction", request.getContextPath() + "/admin/products/add");
            request.setAttribute("formTitle", "Add Product");
            request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
        }
    }
}