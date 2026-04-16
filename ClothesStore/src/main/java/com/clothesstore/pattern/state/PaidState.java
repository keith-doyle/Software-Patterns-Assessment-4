package com.clothesstore.pattern.state;

import java.util.List;

public class PaidState implements OrderState {
    @Override
    public String getName() {
        return "PAID";
    }

    @Override
    public List<String> getAllowedTransitions() {
        return List.of("SHIPPED", "CANCELLED");
    }
}