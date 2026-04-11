package com.clothesstore.pattern.strategy;

public class SortByTitleDesc implements SortStrategy {
    @Override
    public String getOrderByClause() {
        return "p.title DESC";
    }
}