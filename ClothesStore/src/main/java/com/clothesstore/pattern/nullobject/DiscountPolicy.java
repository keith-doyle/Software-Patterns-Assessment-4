package com.clothesstore.pattern.nullobject;

public interface DiscountPolicy {
    double apply(double price);
    String getDescription();
}