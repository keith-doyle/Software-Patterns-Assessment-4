package com.clothesstore.pattern.state;

import java.util.List;

public class CancelledState implements OrderState {
    @Override
    public String getName() {
        return "CANCELLED";
    }

    @Override
    public List<String> getAllowedTransitions() {
        return List.of();
    }
}