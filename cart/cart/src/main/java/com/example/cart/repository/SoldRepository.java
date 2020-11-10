package com.example.cart.repository;

import com.example.cart.model.Sold;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoldRepository extends JpaRepository<Sold, Long> {
}
