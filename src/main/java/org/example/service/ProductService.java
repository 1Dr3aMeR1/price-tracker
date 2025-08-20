package org.example.service;

import org.example.model.Product;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Product createProduct(int userId, String name, BigDecimal desiredPrice);
    List<Product> getUserProducts(int userId);
    void deleteProduct(int productId);
}