package com.essoft.debezium.service;

import com.essoft.debezium.model.Product;
import com.essoft.debezium.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long productId, Product product) {
        final Product currentProduct = productRepository.findById(productId).orElse(null);
        currentProduct.setName(product.getName());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setStock(product.getStock());
        return productRepository.save(currentProduct);
    }
}
