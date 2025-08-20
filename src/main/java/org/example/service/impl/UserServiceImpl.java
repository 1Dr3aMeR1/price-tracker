package org.example.service.impl;

import org.example.model.User;
import org.example.repository.impl.UserRepositoryImpl;

import java.sql.SQLException;

public class UserServiceImpl {
    private final UserRepositoryImpl userRepository = new UserRepositoryImpl();

    public UserServiceImpl() throws SQLException {
    }

    public void registerUser(long telegramId, String username) {
        User user = new User();
        user.setTelegramId(telegramId);
        user.setUsername(username);
        userRepository.save(user);

    }
}