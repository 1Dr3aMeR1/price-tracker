package org.example.repository;

import org.example.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);                 // создать/обновить продукт
    Optional<Product> findById(int id);            // найти по id
    List<Product> findByUserId(int userId);        // все товары юзера
    void delete(int id);                           // удалить
}