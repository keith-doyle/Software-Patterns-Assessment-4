package com.clothesstore.pattern.observer;

import com.clothesstore.service.ProductService;

public class RatingObserver implements ProductObserver {

    private final ProductService productService = new ProductService();

    @Override
    public void update(int productId) {
        double averageRating = productService.getAverageRatingFromReviews(productId);
        productService.updateAverageRating(productId, averageRating);
    }
}