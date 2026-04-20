package com.clothesstore.pattern.strategy;

public class SortByManufacturerDesc implements SortStrategy {
    @Override
    public String getOrderByClause() {
        return "m.name DESC";
    }
}