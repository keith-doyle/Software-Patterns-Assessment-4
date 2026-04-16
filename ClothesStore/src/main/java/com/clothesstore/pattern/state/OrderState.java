package com.clothesstore.pattern.state;

import java.util.List;

public interface OrderState {
    String getName();
    List<String> getAllowedTransitions();
}