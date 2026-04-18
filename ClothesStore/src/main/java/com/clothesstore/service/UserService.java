package com.clothesstore.service;

import com.clothesstore.dao.UserDAO;
import com.clothesstore.model.User;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public boolean updateLoyaltyPoints(int userId, int loyaltyPoints) {
        return userDAO.updateLoyaltyPoints(userId, loyaltyPoints);
    }
}