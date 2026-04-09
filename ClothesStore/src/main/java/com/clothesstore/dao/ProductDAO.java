package com.clothesstore.dao;

import com.clothesstore.model.Product;
import com.clothesstore.pattern.singleton.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private static final String GET_ALL_PRODUCTS = """
        SELECT p.product_id, p.title, p.description, p.price, p.stock_quantity,
               p.image_path, c.name AS category_name, m.name AS manufacturer_name,
               p.average_rating, p.active
        FROM products p
        LEFT JOIN categories c ON p.category_id = c.category_id
        LEFT JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id
        WHERE p.active = TRUE
        ORDER BY p.title ASC
        """;

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_PRODUCTS);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setTitle(rs.getString("title"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setImagePath(rs.getString("image_path"));
                product.setCategoryName(rs.getString("category_name"));
                product.setManufacturerName(rs.getString("manufacturer_name"));
                product.setAverageRating(rs.getDouble("average_rating"));
                product.setActive(rs.getBoolean("active"));

                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}