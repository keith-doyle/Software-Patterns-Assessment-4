package com.clothesstore.pattern.command;

import com.clothesstore.service.ProductService;

public class ReplenishStockCommand implements Command {

    private final ProductService productService;
    private final int productId;
    private final int quantity;

    public ReplenishStockCommand(ProductService productService, int productId, int quantity) {
        this.productService = productService;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public boolean execute() {
        return productService.increaseStock(productId, quantity);
    }
}