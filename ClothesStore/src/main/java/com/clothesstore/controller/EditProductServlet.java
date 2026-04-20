package com.clothesstore.controller;

import com.clothesstore.model.Product;
import com.clothesstore.service.ProductService;
import com.clothesstore.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/products/edit")
public class EditProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int productId = Integer.parseInt(request.getParameter("id"));
        Product product = productService.getProductById(productId);

        request.setAttribute("product", product);
        request.setAttribute("categories", productService.getAllCategories());
        request.setAttribute("manufacturers", productService.getAllManufacturers());
        request.setAttribute("formAction", request.getContextPath() + "/admin/products/edit");
        request.setAttribute("formTitle", "Edit Product");
        request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productId = request.getParameter("productId");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String stockQuantity = request.getParameter("stockQuantity");
        String imagePath = request.getParameter("imagePath");
        String categoryName = request.getParameter("category");
        String manufacturerName = request.getParameter("manufacturer");

        if (ValidationUtil.isNullOrBlank(productId) ||
            ValidationUtil.isNullOrBlank(title) ||
            !ValidationUtil.isPositiveDouble(price) ||
            !ValidationUtil.isNonNegativeInteger(stockQuantity) ||
            ValidationUtil.isNullOrBlank(categoryName) ||
            ValidationUtil.isNullOrBlank(manufacturerName)) {

            request.setAttribute("errorMessage", "Please enter valid product details.");
            request.setAttribute("categories", productService.getAllCategories());
            request.setAttribute("manufacturers", productService.getAllManufacturers());
            request.setAttribute("formAction", request.getContextPath() + "/admin/products/edit");
            request.setAttribute("formTitle", "Edit Product");
            request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
            return;
        }

        Product product = new Product();
        product.setProductId(Integer.parseInt(productId));
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(Double.parseDouble(price));
        product.setStockQuantity(Integer.parseInt(stockQuantity));
        product.setImagePath(imagePath);
        product.setActive("on".equals(request.getParameter("active")));

        boolean success = productService.updateProduct(product, categoryName, manufacturerName);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/admin/products");
        } else {
            request.setAttribute("errorMessage", "Failed to update product.");
            request.setAttribute("product", product);
            request.setAttribute("categories", productService.getAllCategories());
            request.setAttribute("manufacturers", productService.getAllManufacturers());
            request.setAttribute("formAction", request.getContextPath() + "/admin/products/edit");
            request.setAttribute("formTitle", "Edit Product");
            request.getRequestDispatcher("/WEB-INF/views/product-form.jsp").forward(request, response);
        }
    }
}