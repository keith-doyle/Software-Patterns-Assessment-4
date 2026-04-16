package com.clothesstore.pattern.state;

import java.util.List;

public class PendingState implements OrderState {
    @Override
    public String getName() {
        return "PENDING";
    }

    @Override
    public List<String> getAllowedTransitions() {
        return List.of("PAID", "CANCELLED");
    }
}