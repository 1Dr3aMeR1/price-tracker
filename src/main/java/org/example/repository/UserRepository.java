package org.example.repository;

import org.example.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByTelegramId(long telegramId);
    User save(User user);
}