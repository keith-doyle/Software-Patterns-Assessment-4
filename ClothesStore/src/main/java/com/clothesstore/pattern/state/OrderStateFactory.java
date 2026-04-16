package com.clothesstore.pattern.state;

public class OrderStateFactory {

    public static OrderState fromString(String status) {
        if (status == null) {
            return new PendingState();
        }

        return switch (status.toUpperCase()) {
            case "PAID" -> new PaidState();
            case "SHIPPED" -> new ShippedState();
            case "DELIVERED" -> new DeliveredState();
            case "CANCELLED" -> new CancelledState();
            default -> new PendingState();
        };
    }
}