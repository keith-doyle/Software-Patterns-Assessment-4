package com.clothesstore.service;

import com.clothesstore.dao.OrderDAO;
import com.clothesstore.model.CartItem;
import com.clothesstore.model.Order;
import com.clothesstore.model.OrderItem;
import com.clothesstore.model.User;
import com.clothesstore.pattern.decorator.BasePrice;
import com.clothesstore.pattern.decorator.LoyaltyDiscountDecorator;
import com.clothesstore.pattern.decorator.PriceComponent;
import com.clothesstore.pattern.decorator.SeasonalDiscountDecorator;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final ProductService productService = new ProductService();
    private final UserService userService = new UserService();

    public int checkout(int userId, List<CartItem> cart) {
        if (cart == null || cart.isEmpty()) {
            return -1;
        }

        double subtotal = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart) {
            subtotal += cartItem.getSubtotal();

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProduct().getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            orderItems.add(orderItem);
        }

        User user = userService.getUserById(userId);

        PriceComponent priceComponent = new BasePrice(subtotal);

        // seasonal discount always active for demo
        priceComponent = new SeasonalDiscountDecorator(priceComponent);

        // loyalty discount only for users with enough points
        if (user != null && user.getLoyaltyPoints() >= 100) {
            priceComponent = new LoyaltyDiscountDecorator(priceComponent);
        }

        double finalTotal = priceComponent.getPrice();

        int orderId = orderDAO.createOrder(userId, finalTotal, "PENDING");
        if (orderId == -1) {
            return -1;
        }

        boolean itemsAdded = orderDAO.addOrderItems(orderId, orderItems);
        if (!itemsAdded) {
            return -1;
        }

        for (CartItem cartItem : cart) {
            boolean stockReduced = productService.reduceStock(
                cartItem.getProduct().getProductId(),
                cartItem.getQuantity()
            );

            if (!stockReduced) {
                return -1;
            }
        }

        if (user != null) {
            int earnedPoints = (int) (finalTotal / 10);
            userService.updateLoyaltyPoints(userId, user.getLoyaltyPoints() + earnedPoints);
        }

        return orderId;
    }

    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        Order order = orderDAO.getOrderById(orderId);

        if (order == null) {
            return false;
        }

        if (!order.canTransitionTo(newStatus)) {
            return false;
        }

        return orderDAO.updateOrderStatus(orderId, newStatus);
    }

    public double calculateCartSubtotal(List<CartItem> cart) {
        double subtotal = 0.0;
        if (cart != null) {
            for (CartItem item : cart) {
                subtotal += item.getSubtotal();
            }
        }
        return subtotal;
    }

    public double calculateDiscountedTotal(int userId, List<CartItem> cart) {
        double subtotal = calculateCartSubtotal(cart);
        User user = userService.getUserById(userId);

        PriceComponent priceComponent = new BasePrice(subtotal);
        priceComponent = new SeasonalDiscountDecorator(priceComponent);

        if (user != null && user.getLoyaltyPoints() >= 100) {
            priceComponent = new LoyaltyDiscountDecorator(priceComponent);
        }

        return priceComponent.getPrice();
    }

    public String getDiscountDescription(int userId) {
        User user = userService.getUserById(userId);

        PriceComponent priceComponent = new BasePrice(0.0);
        priceComponent = new SeasonalDiscountDecorator(priceComponent);

        if (user != null && user.getLoyaltyPoints() >= 100) {
            priceComponent = new LoyaltyDiscountDecorator(priceComponent);
        }

        return priceComponent.getDescription();
    }
}