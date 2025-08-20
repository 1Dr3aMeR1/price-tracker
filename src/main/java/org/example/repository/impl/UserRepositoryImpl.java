package org.example.repository.impl;

import org.example.config.Database;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.sql.*;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final Connection connection = Database.getConnection();

    public UserRepositoryImpl() throws SQLException {
    }

    @Override
    public Optional<User> findByTelegramId(long telegramId) {
        String sql = "SELECT id, telegram_id, username, created_at FROM users WHERE telegram_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, telegramId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setTelegramId(rs.getLong("telegram_id"));
                user.setUsername(rs.getString("username"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    user.setCreatedAt(ts.toLocalDateTime());
                }
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by telegramId", e);
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (telegram_id, username, created_at) VALUES (?, ?, now()) " + "ON CONFLICT (telegram_id) DO NOTHING RETURNING id";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, user.getTelegramId());
            stmt.setString(2, user.getUsername());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }
}