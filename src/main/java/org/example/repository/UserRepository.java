package org.example.repository;

import java.sql.*;

public class UserRepository {

    public void saveUser(long telegramId, String username) {
        String sql = "INSERT INTO users (telegram_id, username) VALUES (?, ?) " +
                "ON CONFLICT (telegram_id) DO NOTHING";

        try (Connection connect = Database.getConnection();
             PreparedStatement stmt = connect.prepareStatement(sql)) {
            stmt.setLong(1, telegramId);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}