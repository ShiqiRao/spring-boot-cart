package com.example.cart.exception;


import com.example.cart.model.Product;

public class NotEnoughProductsInStockException extends Exception {

    private static final String DEFAULT_MESSAGE = "库存产品不足";

    public NotEnoughProductsInStockException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughProductsInStockException(Product product) {
        super(String.format("%s 库存不足。 仅剩 %d 件。", product.getName(), product.getQuantity()));
    }

}
