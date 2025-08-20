package org.example.service.impl;

import org.example.model.Product;
import org.example.model.User;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.ProductService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private UserRepository userRepository = new UserRepositoryImpl();

    public ProductServiceImpl(ProductRepository productRepository) throws SQLException {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(int userId, String name, BigDecimal desiredPrice) {
        User user = userRepository.findByTelegramId(userId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setTelegramId(userId);
                    return userRepository.save(newUser);
                });

        Product product = new Product();
        product.setUserId(user.getId());
        product.setName(name);
        product.setDesiredPrice(desiredPrice);
        product.setCurrentMinPrice(null);

        return productRepository.save(product);
    }

    @Override
    public List<Product> getUserProducts(int userId) {
        return productRepository.findByUserId(userId);
    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.delete(productId);
    }
}