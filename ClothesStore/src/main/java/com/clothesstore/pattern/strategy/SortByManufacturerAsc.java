package com.clothesstore.pattern.strategy;

public class SortByManufacturerAsc implements SortStrategy {
    @Override
    public String getOrderByClause() {
        return "m.name ASC";
    }
}