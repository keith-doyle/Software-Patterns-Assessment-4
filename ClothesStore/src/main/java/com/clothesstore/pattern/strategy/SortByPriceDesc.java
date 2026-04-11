package com.clothesstore.pattern.strategy;

public class SortByPriceDesc implements SortStrategy {
    @Override
    public String getOrderByClause() {
        return "p.price DESC";
    }
}