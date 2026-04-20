package com.clothesstore.pattern.factory;

import com.clothesstore.pattern.strategy.SortByManufacturerAsc;
import com.clothesstore.pattern.strategy.SortByManufacturerDesc;
import com.clothesstore.pattern.strategy.SortByPriceAsc;
import com.clothesstore.pattern.strategy.SortByPriceDesc;
import com.clothesstore.pattern.strategy.SortByTitleAsc;
import com.clothesstore.pattern.strategy.SortByTitleDesc;
import com.clothesstore.pattern.strategy.SortStrategy;

public class SortStrategyFactory {

    public static SortStrategy getStrategy(String sortOption) {
        if (sortOption == null || sortOption.isBlank()) {
            return new SortByTitleAsc();
        }

        return switch (sortOption) {
            case "titleDesc" -> new SortByTitleDesc();
            case "priceAsc" -> new SortByPriceAsc();
            case "priceDesc" -> new SortByPriceDesc();
            case "manufacturerAsc" -> new SortByManufacturerAsc();
            case "manufacturerDesc" -> new SortByManufacturerDesc();
            default -> new SortByTitleAsc();
        };
    }
}