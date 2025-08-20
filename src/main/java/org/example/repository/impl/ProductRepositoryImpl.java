package org.example.repository.impl;

import org.example.config.Database;
import org.example.model.Product;
import org.example.repository.ProductRepository;

import java.sql.*;
import java.util.*;

public class ProductRepositoryImpl implements ProductRepository {

    private final Connection connection;

    {
        try {
            connection = Database.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProductRepositoryImpl() throws SQLException {
    }


    @Override
    public Product save(Product product) {
        try {
            if (product.getId() == 0) {
                String sql = """
                    INSERT INTO products (user_id, name, desired_price, current_min_price, created_at)
                    VALUES (?, ?, ?, ?, now())
                    RETURNING id, created_at
                """;

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, product.getUserId());
                    stmt.setString(2, product.getName());
                    stmt.setBigDecimal(3, product.getDesiredPrice());
                    stmt.setBigDecimal(4, product.getCurrentMinPrice());

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        product.setId(rs.getInt("id"));
                        product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    }
                }
            } else {
                String sql = """
                    UPDATE products
                    SET name = ?, desired_price = ?, current_min_price = ?
                    WHERE id = ?
                """;

                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, product.getName());
                    stmt.setBigDecimal(2, product.getDesiredPrice());
                    stmt.setBigDecimal(3, product.getCurrentMinPrice());
                    stmt.setInt(4, product.getId());
                    stmt.executeUpdate();
                }
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving product", e);
        }
    }

    @Override
    public Optional<Product> findById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding product by id", e);
        }
    }

    @Override
    public List<Product> findByUserId(int userId) {
        List<Product> products = new ArrayList<>();

        String sql = """
        SELECT p.*
        FROM products p
        JOIN users u ON p.user_id = u.id
        WHERE u.telegram_id = ?
        ORDER BY p.created_at DESC
    """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding products by telegram id", e);
        }
        return products;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting product", e);
        }
    }


    private Product mapRow(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setUserId(rs.getInt("user_id"));
        product.setName(rs.getString("name"));
        product.setDesiredPrice(rs.getBigDecimal("desired_price"));
        product.setCurrentMinPrice(rs.getBigDecimal("current_min_price"));

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            product.setCreatedAt(ts.toLocalDateTime());
        }

        return product;
    }
}