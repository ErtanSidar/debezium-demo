package com.essoft.debezium.repository;

import com.essoft.debezium.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
