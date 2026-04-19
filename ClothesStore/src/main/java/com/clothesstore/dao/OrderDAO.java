package com.clothesstore.dao;

import com.clothesstore.model.Order;
import com.clothesstore.model.OrderItem;
import com.clothesstore.pattern.singleton.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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

    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();

        String sql = """
            SELECT * FROM orders
            WHERE user_id = ?
            ORDER BY created_at DESC
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRowToOrder(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        String sql = """
            SELECT * FROM orders
            ORDER BY created_at DESC
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                orders.add(mapRowToOrder(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order getOrderById(int orderId) {
        String sql = """
            SELECT * FROM orders
            WHERE order_id = ?
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapRowToOrder(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = """
            UPDATE orders
            SET status = ?
            WHERE order_id = ?
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newStatus);
            statement.setInt(2, orderId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private Order mapRowToOrder(ResultSet rs) throws Exception {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalPrice(rs.getDouble("total_price"));
        order.setStatus(rs.getString("status"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        return order;
    }
    public java.util.List<Order> getOrdersForAdminByUserId(int userId) {
        java.util.List<Order> orders = new java.util.ArrayList<>();

        String sql = """
            SELECT * FROM orders
            WHERE user_id = ?
            ORDER BY created_at DESC
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapRowToOrder(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
}