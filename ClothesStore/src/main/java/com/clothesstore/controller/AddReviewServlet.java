package com.clothesstore.controller;

import com.clothesstore.model.Review;
import com.clothesstore.model.User;
import com.clothesstore.service.ReviewService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/add-review")
public class AddReviewServlet extends HttpServlet {

    private final ReviewService reviewService = new ReviewService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null || !"CUSTOMER".equals(loggedInUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int productId = Integer.parseInt(request.getParameter("productId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");

        Review review = new Review();
        review.setUserId(loggedInUser.getUserId());
        review.setProductId(productId);
        review.setRating(rating);
        review.setComment(comment);

        reviewService.addReview(review);

        response.sendRedirect(request.getContextPath() + "/product-details?id=" + productId);
    }
}