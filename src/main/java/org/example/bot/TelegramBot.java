package org.example.bot;

import org.example.model.Product;
import org.example.repository.impl.ProductRepositoryImpl;
import org.example.service.ProductService;
import org.example.service.impl.ProductServiceImpl;

import org.example.service.impl.UserServiceImpl;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.*;

public class TelegramBot extends TelegramLongPollingBot {

    private final String username;
    private final String token;
    private final ProductService productService;

    private final Map<Long, String> userStates = new HashMap<>();

    public TelegramBot(String username, String token) throws SQLException {
        this.username = username;
        this.token = token;
        this.productService = new ProductServiceImpl(new ProductRepositoryImpl());
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String text = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        try {
            if ("WAITING_PRODUCT_NAME".equals(userStates.get(chatId))) {
                String productName = text.trim();
                if (productName.isEmpty()) {
                    sendText(chatId, "❌ Название не может быть пустым. Попробуйте снова.");
                    return;
                }

                productService.createProduct((int) chatId, productName, null);
                sendText(chatId, "✅ Товар \"" + productName + "\" создан. Теперь добавьте лоты через меню.");
                userStates.remove(chatId); // сброс состояния
                return;
            }

            if (text.equals("/start")) {
                new UserServiceImpl().registerUser(chatId, update.getMessage().getFrom().getUserName());
                sendMessage(ButtonKeboard.withMainMenu(chatId, "Привет! Выбери действие:"));
            } else if (text.equals("➕ Добавить товар")) {
                sendText(chatId, "Введите название товара:");
                userStates.put(chatId, "WAITING_PRODUCT_NAME");
            } else if (text.equals("📋 Мои товары")) {
                List<Product> products = productService.getUserProducts((int) chatId);
                if (products.isEmpty()) {
                    sendText(chatId, "У вас пока нет товаров.");
                } else {
                    StringBuilder sb = new StringBuilder("Ваши товары:\n");
                    int i = 1;
                    for (Product p : products) {
                        String minPrice = (p.getCurrentMinPrice() == null) ? "ещё нет лотов" : p.getCurrentMinPrice() + " ₽";
                        sb.append(i++).append(". ")
                                .append(p.getName())
                                .append(" — ").append(minPrice).append("\n");
                    }
                    sendText(chatId, sb.toString());
                }
            } else {
                sendText(chatId, "Неизвестная команда. Используйте меню.");
            }

        } catch (Exception e) {
            sendText(chatId, "⚠ Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendText(long chatId, String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}