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

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private Map<Product, Integer> products = new HashMap<>();


    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
    }

    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 1)
                products.replace(product, products.get(product) - 1);
            else if (products.get(product) == 1) {
                products.remove(product);
            }
        }
    }

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
