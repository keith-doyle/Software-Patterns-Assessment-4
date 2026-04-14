package com.clothesstore.dao;

import com.clothesstore.model.OrderItem;
import com.clothesstore.pattern.singleton.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class OrderDAO {

    public int createOrder(int userId, double totalPrice, String status) {
        String sql = """
            INSERT INTO orders (user_id, total_price, status)
            VALUES (?, ?, ?)
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, userId);
            statement.setDouble(2, totalPrice);
            statement.setString(3, status);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean addOrderItems(int orderId, List<OrderItem> orderItems) {
        String sql = """
            INSERT INTO order_items (order_id, product_id, quantity, unit_price)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (OrderItem item : orderItems) {
                statement.setInt(1, orderId);
                statement.setInt(2, item.getProductId());
                statement.setInt(3, item.getQuantity());
                statement.setDouble(4, item.getUnitPrice());
                statement.addBatch();
            }

            int[] results = statement.executeBatch();
            return results.length == orderItems.size();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}