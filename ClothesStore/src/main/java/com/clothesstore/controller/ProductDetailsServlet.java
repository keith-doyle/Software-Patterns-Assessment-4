package com.clothesstore.controller;

import com.clothesstore.model.Product;
import com.clothesstore.model.Review;
import com.clothesstore.service.ProductService;
import com.clothesstore.service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {

    private final ProductService productService = new ProductService();
    private final ReviewService reviewService = new ReviewService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int productId = Integer.parseInt(request.getParameter("id"));

        Product product = productService.getActiveProductById(productId);
        List<Review> reviews = reviewService.getReviewsByProductId(productId);

        request.setAttribute("product", product);
        request.setAttribute("reviews", reviews);

        request.getRequestDispatcher("/WEB-INF/views/product-details.jsp").forward(request, response);
    }
}