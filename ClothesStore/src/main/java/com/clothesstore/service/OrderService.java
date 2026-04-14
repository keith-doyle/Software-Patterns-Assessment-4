package com.clothesstore.service;

import com.clothesstore.dao.OrderDAO;
import com.clothesstore.model.CartItem;
import com.clothesstore.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();
    private final ProductService productService = new ProductService();

    public int checkout(int userId, List<CartItem> cart) {
        if (cart == null || cart.isEmpty()) {
            return -1;
        }

        double total = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart) {
            total += cartItem.getSubtotal();

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProduct().getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getProduct().getPrice());
            orderItems.add(orderItem);
        }

        int orderId = orderDAO.createOrder(userId, total, "PENDING");
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

        return orderId;
    }
}