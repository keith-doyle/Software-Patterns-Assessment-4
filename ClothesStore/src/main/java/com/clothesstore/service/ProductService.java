package com.clothesstore.service;

import com.clothesstore.dao.ProductDAO;
import com.clothesstore.model.Product;
import com.clothesstore.pattern.factory.SortStrategyFactory;
import com.clothesstore.pattern.strategy.SortStrategy;

import java.util.List;

public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();

    public List<Product> getFilteredProducts(String title, String category, String manufacturer, String sortOption) {
        SortStrategy strategy = SortStrategyFactory.getStrategy(sortOption);
        return productDAO.searchProducts(title, category, manufacturer, strategy.getOrderByClause());
    }

    public List<String> getAllCategories() {
        return productDAO.getAllCategories();
    }

    public List<String> getAllManufacturers() {
        return productDAO.getAllManufacturers();
    }
}