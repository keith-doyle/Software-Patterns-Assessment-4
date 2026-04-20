package com.clothesstore.controller;

import com.clothesstore.model.Review;
import com.clothesstore.model.User;
import com.clothesstore.service.ReviewService;
import com.clothesstore.util.ValidationUtil;

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

        int productId = Integer.parseInt(request.getParameter("productId"));
        String ratingValue = request.getParameter("rating");
        String comment = request.getParameter("comment");

        if (!ValidationUtil.isRatingValid(ratingValue)) {
            response.sendRedirect(request.getContextPath() + "/product-details?id=" + productId);
            return;
        }

        Review review = new Review();
        review.setUserId(loggedInUser.getUserId());
        review.setProductId(productId);
        review.setRating(Integer.parseInt(ratingValue));
        review.setComment(comment);

        reviewService.addReview(review);

        response.sendRedirect(request.getContextPath() + "/product-details?id=" + productId);
    }
}