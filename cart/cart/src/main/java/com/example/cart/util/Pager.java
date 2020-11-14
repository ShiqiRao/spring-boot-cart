package com.example.cart.util;


import com.example.cart.model.Product;
import org.springframework.data.domain.Page;


public class Pager {

    private final Page<Product> products;

    public Pager(Page<Product> products) {
        this.products = products;
    }
    /*获取当前页数*/
    public int getPageIndex() {
        return products.getNumber() + 1;
    }
    /*获取页大小*/
    public int getPageSize() {
        return products.getSize();
    }
    /*是否有下页*/
    public boolean hasNext() {
        return products.hasNext();
    }
    /*是否有上页*/
    public boolean hasPrevious() {
        return products.hasPrevious();
    }
    /*总页数*/
    public int getTotalPages() {
        return products.getTotalPages();
    }
    /*总元素个数*/
    public long getTotalElements() {
        return products.getTotalElements();
    }
    /*当前页是否超出页数上限*/
    public boolean indexOutOfBounds() {
        return this.getPageIndex() < 0 || this.getPageIndex() > this.getTotalElements();
    }
}