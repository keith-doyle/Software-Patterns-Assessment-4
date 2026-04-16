package com.clothesstore.pattern.state;

import java.util.List;

public class ShippedState implements OrderState {
    @Override
    public String getName() {
        return "SHIPPED";
    }

    @Override
    public List<String> getAllowedTransitions() {
        return List.of("DELIVERED");
    }
}