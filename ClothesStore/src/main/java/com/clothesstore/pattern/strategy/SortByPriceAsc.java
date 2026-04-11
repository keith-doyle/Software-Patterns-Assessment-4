package com.clothesstore.pattern.strategy;

public class SortByPriceAsc implements SortStrategy {
    @Override
    public String getOrderByClause() {
        return "p.price ASC";
    }
}