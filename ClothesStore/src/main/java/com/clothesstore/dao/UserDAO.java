package com.clothesstore.dao;

import com.clothesstore.model.User;
import com.clothesstore.pattern.singleton.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    private static final String INSERT_USER = """
        INSERT INTO users (full_name, email, password, role, address, payment_method, loyalty_points)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    private static final String FIND_BY_EMAIL_AND_PASSWORD = """
        SELECT * FROM users
        WHERE email = ? AND password = ?
        """;

    private static final String FIND_BY_EMAIL = """
        SELECT * FROM users
        WHERE email = ?
        """;

    public boolean registerUser(User user) {
        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {

            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getPaymentMethod());
            statement.setInt(7, user.getLoyaltyPoints());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User loginUser(String email, String password) {
        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD)) {

            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    user.setAddress(rs.getString("address"));
                    user.setPaymentMethod(rs.getString("payment_method"));
                    user.setLoyaltyPoints(rs.getInt("loyalty_points"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    return user;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean emailExists(String email) {
        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL)) {

            statement.setString(1, email);

            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    user.setAddress(rs.getString("address"));
                    user.setPaymentMethod(rs.getString("payment_method"));
                    user.setLoyaltyPoints(rs.getInt("loyalty_points"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    return user;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateLoyaltyPoints(int userId, int loyaltyPoints) {
        String sql = "UPDATE users SET loyalty_points = ? WHERE user_id = ?";

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, loyaltyPoints);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public java.util.List<User> getAllCustomers() {
        java.util.List<User> customers = new java.util.ArrayList<>();

        String sql = """
            SELECT * FROM users
            WHERE role = 'CUSTOMER'
            ORDER BY created_at DESC
            """;

        try (Connection connection = DBConnectionManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setAddress(rs.getString("address"));
                user.setPaymentMethod(rs.getString("payment_method"));
                user.setLoyaltyPoints(rs.getInt("loyalty_points"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                customers.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customers;
    }
    
}