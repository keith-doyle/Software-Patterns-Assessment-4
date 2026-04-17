package com.clothesstore.service;

import com.clothesstore.dao.ReviewDAO;
import com.clothesstore.model.Review;
import com.clothesstore.pattern.observer.RatingObserver;
import com.clothesstore.pattern.observer.ReviewEventManager;

import java.util.List;

public class ReviewService {

    private final ReviewDAO reviewDAO = new ReviewDAO();
    private final ReviewEventManager reviewEventManager = new ReviewEventManager();

    public ReviewService() {
        reviewEventManager.attach(new RatingObserver());
    }

    public boolean addReview(Review review) {
        boolean added = reviewDAO.addReview(review);

        if (added) {
            reviewEventManager.notifyObservers(review.getProductId());
        }

        return added;
    }

    public List<Review> getReviewsByProductId(int productId) {
        return reviewDAO.getReviewsByProductId(productId);
    }
}