package com.clothesstore.controller;

import com.clothesstore.model.Product;
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

        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String manufacturer = request.getParameter("manufacturer");
        String sort = request.getParameter("sort");

        List<Product> products = productService.getFilteredProductsForAdmin(title, category, manufacturer, sort);
        List<String> categories = productService.getAllCategories();
        List<String> manufacturers = productService.getAllManufacturers();

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("manufacturers", manufacturers);

        request.setAttribute("selectedTitle", title);
        request.setAttribute("selectedCategory", category);
        request.setAttribute("selectedManufacturer", manufacturer);
        request.setAttribute("selectedSort", sort);

        request.getRequestDispatcher("/WEB-INF/views/admin-products.jsp").forward(request, response);
    }
}