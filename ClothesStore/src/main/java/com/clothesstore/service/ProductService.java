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

    public List<Product> getAllProductsForAdmin() {
        return productDAO.getAllProductsForAdmin();
    }

    public Product getProductById(int productId) {
        return productDAO.getProductById(productId);
    }

    public boolean addProduct(Product product, String categoryName, String manufacturerName) {
        int categoryId = productDAO.getCategoryIdByName(categoryName);
        int manufacturerId = productDAO.getManufacturerIdByName(manufacturerName);

        if (categoryId == -1 || manufacturerId == -1) {
            return false;
        }

        return productDAO.addProduct(product, categoryId, manufacturerId);
    }

    public boolean updateProduct(Product product, String categoryName, String manufacturerName) {
        int categoryId = productDAO.getCategoryIdByName(categoryName);
        int manufacturerId = productDAO.getManufacturerIdByName(manufacturerName);

        if (categoryId == -1 || manufacturerId == -1) {
            return false;
        }

        return productDAO.updateProduct(product, categoryId, manufacturerId);
    }

    public boolean deleteProduct(int productId) {
        return productDAO.deleteProduct(productId);
    }

    public boolean reduceStock(int productId, int quantity) {
        return productDAO.reduceStock(productId, quantity);
    }
    
    public List<String> getAllCategories() {
        return productDAO.getAllCategories();
    }

    public List<String> getAllManufacturers() {
        return productDAO.getAllManufacturers();
    }
    
    public Product getActiveProductById(int productId) {
        return productDAO.getActiveProductById(productId);
    }
}