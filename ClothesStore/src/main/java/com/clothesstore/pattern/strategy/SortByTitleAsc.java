package com.clothesstore.pattern.strategy;

public class SortByTitleAsc implements SortStrategy {
    @Override
    public String getOrderByClause() {
        return "p.title ASC";
    }
}