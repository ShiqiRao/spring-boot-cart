package com.example.cart.service;


import com.example.cart.exception.NotEnoughProductsInStockException;
import com.example.cart.model.Order;
import com.example.cart.model.Product;
import com.example.cart.model.Sold;
import com.example.cart.model.User;
import com.example.cart.repository.OrderRepository;
import com.example.cart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Shopping Cart is implemented with a Map, and as a session bean
 *
 * @author Dusan
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private Map<Product, Integer> products = new HashMap<>();


    /**
     * If product is in the map just increment quantity by 1.
     * If product is not in the map with, add it with quantity 1
     *
     * @param product
     */
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    /**
     * If product is in the map with quantity > 1, just decrement quantity by 1.
     * If product is in the map with quantity 1, remove it from map
     *
     * @param product
     */
    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

    /**
     * @return unmodifiable copy of the map
     */
    public Map<Product, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    public void checkout(User user) throws NotEnoughProductsInStockException {
        Product product;
        Order order = new Order()
                .setCreateTime(LocalDateTime.now())
                .setUser(user);
        BigDecimal payment = BigDecimal.ZERO;
        List<Sold> soldList = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            // Refresh quantity for every product before checking
            Product key = entry.getKey();
            Integer quantity = entry.getValue();
            Optional<Product> one = productRepository.findOne(Example.of(key));
            if (!one.isPresent()) {
                throw new IllegalArgumentException("");
            }
            product = one.get();
            if (product.getQuantity() < quantity) {
                throw new NotEnoughProductsInStockException(product);
            }
            entry.getKey().setQuantity(product.getQuantity() - quantity);
            soldList.add(new Sold()
                    .setQuantity(quantity)
                    .setProduct(key)
                    .setOrder(order));
            payment = payment.add(key.getPrice());
        }
        order.setPayment(payment)
                .setSoldList(soldList);
        orderRepository.save(order);
        productRepository.saveAll(products.keySet());
        productRepository.flush();
        products.clear();
    }

    public BigDecimal getTotal() {
        return products.entrySet().stream()
                .map(entry -> entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
