package com.clothesstore.dao;

import com.clothesstore.model.Product;
import com.clothesstore.pattern.singleton.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> searchProducts(String title, String category, String manufacturer, String orderByClause) {
        List<Product> products = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT p.product_id, p.title, p.description, p.price, p.stock_quantity,
                   p.image_path, c.name AS category_name, m.name AS manufacturer_name,
                   p.average_rating, p.active
            FROM products p
            LEFT JOIN categories c ON p.category_id = c.category_id
            LEFT JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id
            WHERE p.active = TRUE
            """);

        List<Object> parameters = new ArrayList<>();

        if (title != null && !title.isBlank()) {
            sql.append(" AND p.title LIKE ?");
            parameters.add("%" + title + "%");
        }

        if (category != null && !category.isBlank()) {
            sql.append(" AND c.name LIKE ?");
            parameters.add("%" + category + "%");
        }

        if (manufacturer != null && !manufacturer.isBlank()) {
            sql.append(" AND m.name = ?");
            parameters.add(manufacturer);
        }

        sql.append(" ORDER BY ").append(orderByClause);

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = statement.executeQuery()) {
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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT name FROM categories ORDER BY name ASC";

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    public List<String> getAllManufacturers() {
        List<String> manufacturers = new ArrayList<>();
        String sql = "SELECT name FROM manufacturers ORDER BY name ASC";

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                manufacturers.add(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return manufacturers;
    }
}