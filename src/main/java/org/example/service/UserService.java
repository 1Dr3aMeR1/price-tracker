package org.example.service;

import org.example.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public void registerUser(long telegramId, String username) {
        userRepository.saveUser(telegramId, username);
    }
}