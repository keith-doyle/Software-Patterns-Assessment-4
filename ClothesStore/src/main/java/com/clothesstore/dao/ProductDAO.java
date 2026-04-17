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
                    products.add(mapRowToProduct(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<Product> getAllProductsForAdmin() {
        List<Product> products = new ArrayList<>();

        String sql = """
            SELECT p.product_id, p.title, p.description, p.price, p.stock_quantity,
                   p.image_path, c.name AS category_name, m.name AS manufacturer_name,
                   p.average_rating, p.active
            FROM products p
            LEFT JOIN categories c ON p.category_id = c.category_id
            LEFT JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id
            ORDER BY p.product_id ASC
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product getProductById(int productId) {
        String sql = """
            SELECT p.product_id, p.title, p.description, p.price, p.stock_quantity,
                   p.image_path, c.name AS category_name, m.name AS manufacturer_name,
                   p.average_rating, p.active, p.category_id, p.manufacturer_id
            FROM products p
            LEFT JOIN categories c ON p.category_id = c.category_id
            LEFT JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id
            WHERE p.product_id = ?
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Product product = mapRowToProduct(rs);
                    return product;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addProduct(Product product, int categoryId, int manufacturerId) {
        String sql = """
            INSERT INTO products (title, description, price, stock_quantity, image_path,
                                  category_id, manufacturer_id, average_rating, active)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getTitle());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStockQuantity());
            statement.setString(5, product.getImagePath());
            statement.setInt(6, categoryId);
            statement.setInt(7, manufacturerId);
            statement.setDouble(8, product.getAverageRating());
            statement.setBoolean(9, product.isActive());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateProduct(Product product, int categoryId, int manufacturerId) {
        String sql = """
            UPDATE products
            SET title = ?, description = ?, price = ?, stock_quantity = ?, image_path = ?,
                category_id = ?, manufacturer_id = ?, active = ?
            WHERE product_id = ?
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getTitle());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStockQuantity());
            statement.setString(5, product.getImagePath());
            statement.setInt(6, categoryId);
            statement.setInt(7, manufacturerId);
            statement.setBoolean(8, product.isActive());
            statement.setInt(9, product.getProductId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);
            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean reduceStock(int productId, int quantity) {
        String sql = """
            UPDATE products
            SET stock_quantity = stock_quantity - ?
            WHERE product_id = ? AND stock_quantity >= ?
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, quantity);
            statement.setInt(2, productId);
            statement.setInt(3, quantity);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
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

    public int getCategoryIdByName(String categoryName) {
        String sql = "SELECT category_id FROM categories WHERE name = ?";

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, categoryName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("category_id");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int getManufacturerIdByName(String manufacturerName) {
        String sql = "SELECT manufacturer_id FROM manufacturers WHERE name = ?";

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, manufacturerName);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("manufacturer_id");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    public Product getActiveProductById(int productId) {
        String sql = """
            SELECT p.product_id, p.title, p.description, p.price, p.stock_quantity,
                   p.image_path, c.name AS category_name, m.name AS manufacturer_name,
                   p.average_rating, p.active
            FROM products p
            LEFT JOIN categories c ON p.category_id = c.category_id
            LEFT JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id
            WHERE p.product_id = ? AND p.active = TRUE
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProduct(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean updateAverageRating(int productId, double averageRating) {
        String sql = """
            UPDATE products
            SET average_rating = ?
            WHERE product_id = ?
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, averageRating);
            statement.setInt(2, productId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public double getAverageRatingFromReviews(int productId) {
        String sql = """
            SELECT AVG(rating) AS avg_rating
            FROM reviews
            WHERE product_id = ?
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_rating");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }  

    private Product mapRowToProduct(ResultSet rs) throws Exception {
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
        return product;
    }
}