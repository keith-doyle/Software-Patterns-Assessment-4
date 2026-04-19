package com.clothesstore.service;

import com.clothesstore.dao.OrderDAO;
import com.clothesstore.dao.UserDAO;
import com.clothesstore.model.Order;
import com.clothesstore.model.User;

import java.util.List;

public class CustomerService {

    private final UserDAO userDAO = new UserDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    public List<User> getAllCustomers() {
        return userDAO.getAllCustomers();
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public List<Order> getOrdersForAdminByUserId(int userId) {
        return orderDAO.getOrdersForAdminByUserId(userId);
    }
}