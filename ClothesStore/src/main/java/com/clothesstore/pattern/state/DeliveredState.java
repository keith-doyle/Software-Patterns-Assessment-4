package com.clothesstore.pattern.state;

import java.util.List;

public class DeliveredState implements OrderState {
    @Override
    public String getName() {
        return "DELIVERED";
    }

    @Override
    public List<String> getAllowedTransitions() {
        return List.of();
    }
}